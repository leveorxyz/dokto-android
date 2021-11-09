package com.toybeth.dokto.twilio.data.sdk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.orhanobut.logger.Logger
import com.toybeth.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybeth.dokto.twilio.data.rest.model.response.AuthServiceError
import com.toybeth.dokto.twilio.data.rest.model.response.AuthServiceException
import com.toybeth.dokto.twilio.data.sdk.RoomEvent.*
import com.toybeth.dokto.twilio.data.sdk.RoomEvent.RemoteParticipantEvent.*
import com.toybeth.dokto.twilio.data.service.VideoService.Companion.startService
import com.toybeth.dokto.twilio.data.service.VideoService.Companion.stopService
import com.twilio.video.*
import com.twilio.video.TwilioException.ROOM_MAX_PARTICIPANTS_EXCEEDED_EXCEPTION
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MICROPHONE_TRACK_NAME = "microphone"
const val CAMERA_TRACK_NAME = "camera"
const val SCREEN_TRACK_NAME = "screen"

class RoomManager @Inject constructor(
    private val context: Context,
    private val videoClient: VideoClient,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private var statsScheduler: StatsScheduler? = null
    private val roomListener = RoomListener()

    @VisibleForTesting(otherwise = PRIVATE)
    internal var roomScope = CoroutineScope(coroutineDispatcher)
    private val mutableRoomEvents: MutableSharedFlow<RoomEvent> = MutableSharedFlow()
    val roomEvents: SharedFlow<RoomEvent> = mutableRoomEvents

    @VisibleForTesting(otherwise = PRIVATE)
    internal var localParticipantManager: LocalParticipantManager =
        LocalParticipantManager(context, this)
    var room: Room? = null

    fun disconnect() {
        room?.disconnect()
    }

    suspend fun connect(identity: String, roomName: String) {
        sendRoomEvent(RoomEvent.Connecting)
        connectToRoom(identity, roomName)
    }

    private suspend fun connectToRoom(identity: String, roomName: String) {
        roomScope.launch {
            try {
                videoClient.connect(java.util.UUID.randomUUID().toString(), roomName, roomListener)
            } catch (e: AuthServiceException) {
                handleTokenException(e, e.error)
            } catch (e: Exception) {
                handleTokenException(e)
            }
        }
    }

    fun sendRoomEvent(roomEvent: RoomEvent) {
        Logger.d("sendRoomEvent: $roomEvent")
        roomScope.launch { mutableRoomEvents.emit(roomEvent) }
    }

    private fun handleTokenException(e: Exception, error: AuthServiceError? = null): Room? {
        Logger.e(e, "Failed to retrieve token")
        sendRoomEvent(RoomEvent.TokenError(serviceError = error))
        return null
    }

    fun onResume() {
        localParticipantManager.onResume()
    }

    fun onPause() {
        localParticipantManager.onPause()
    }

    fun toggleLocalVideo() {
        localParticipantManager.toggleLocalVideo()
    }

    fun toggleLocalAudio() {
        localParticipantManager.toggleLocalAudio()
    }

    fun startScreenCapture(captureResultCode: Int, captureIntent: Intent) {
        localParticipantManager.startScreenCapture(captureResultCode, captureIntent)
    }

    fun stopScreenCapture() {
        localParticipantManager.stopScreenCapture()
    }

    fun switchCamera() = localParticipantManager.switchCamera()

    fun sendStatsUpdate(statsReports: List<StatsReport>) {
        room?.let { room ->
            val roomStats = RoomStats(
                room.remoteParticipants,
                localParticipantManager.localVideoTrackNames,
                statsReports
            )
            sendRoomEvent(StatsUpdate(roomStats))
        }
    }

    fun enableLocalAudio() = localParticipantManager.enableLocalAudio()

    fun disableLocalAudio() = localParticipantManager.disableLocalAudio()

    fun enableLocalVideo() = localParticipantManager.enableLocalVideo()

    fun disableLocalVideo() = localParticipantManager.disableLocalVideo()

    inner class RoomListener : Room.Listener {
        override fun onConnected(room: Room) {
            Logger.i(
                "onConnected -> room sid: %s",
                room.sid
            )

            startService(context, room.name)

            setupParticipants(room)

            statsScheduler = StatsScheduler(this@RoomManager, room).apply { start() }
            this@RoomManager.room = room
        }

        override fun onDisconnected(room: Room, twilioException: TwilioException?) {
            Logger.i(
                "Disconnected from room -> sid: %s, state: %s",
                room.sid, room.state
            )

            stopService(context)

            sendRoomEvent(Disconnected)

            localParticipantManager.localParticipant = null

            statsScheduler?.stop()
            statsScheduler = null
        }

        override fun onConnectFailure(room: Room, twilioException: TwilioException) {
            Logger.e(
                "Failed to connect to room -> sid: %s, state: %s, code: %d, error: %s",
                room.sid,
                room.state,
                twilioException.code,
                twilioException.message
            )

            if (twilioException.code == ROOM_MAX_PARTICIPANTS_EXCEEDED_EXCEPTION) {
                sendRoomEvent(MaxParticipantFailure)
            } else {
                sendRoomEvent(ConnectFailure)
            }
        }

        override fun onParticipantConnected(room: Room, remoteParticipant: RemoteParticipant) {
            Logger.i(
                "RemoteParticipant connected -> room sid: %s, remoteParticipant: %s",
                room.sid, remoteParticipant.sid
            )

            remoteParticipant.setListener(RemoteParticipantListener(this@RoomManager))
            sendRoomEvent(RemoteParticipantConnected(remoteParticipant))
        }

        override fun onParticipantDisconnected(room: Room, remoteParticipant: RemoteParticipant) {
            Logger.i(
                "RemoteParticipant disconnected -> room sid: %s, remoteParticipant: %s",
                room.sid, remoteParticipant.sid
            )

            sendRoomEvent(RemoteParticipantDisconnected(remoteParticipant.sid))
        }

        override fun onDominantSpeakerChanged(room: Room, remoteParticipant: RemoteParticipant?) {
            Logger.i(
                "DominantSpeakerChanged -> room sid: %s, remoteParticipant: %s",
                room.sid, remoteParticipant?.sid
            )

            sendRoomEvent(DominantSpeakerChanged(remoteParticipant?.sid))
        }

        override fun onRecordingStarted(room: Room) = sendRoomEvent(RecordingStarted)

        override fun onRecordingStopped(room: Room) = sendRoomEvent(RecordingStopped)

        override fun onReconnected(room: Room) {
            Logger.i("onReconnected: %s", room.name)
        }

        override fun onReconnecting(room: Room, twilioException: TwilioException) {
            Logger.i("onReconnecting: %s", room.name)
        }

        private fun setupParticipants(room: Room) {
            room.localParticipant?.let { localParticipant ->
                localParticipantManager.localParticipant = localParticipant
                val participants = mutableListOf<Participant>()
                participants.add(localParticipant)
                localParticipant.setListener(LocalParticipantListener(this@RoomManager))

                room.remoteParticipants.forEach {
                    it.setListener(RemoteParticipantListener(this@RoomManager))
                    participants.add(it)
                }

                sendRoomEvent(Connected(participants, room, room.name))
                localParticipantManager.publishLocalTracks()
            }
        }
    }
}
