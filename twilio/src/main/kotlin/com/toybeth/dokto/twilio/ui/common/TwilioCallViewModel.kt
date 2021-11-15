package com.toybeth.dokto.twilio.ui.common

import android.Manifest
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.toybeth.dokto.twilio.data.sdk.RoomEvent
import com.toybeth.dokto.twilio.data.sdk.RoomEvent.LocalParticipantEvent
import com.toybeth.dokto.twilio.data.sdk.RoomEvent.RemoteParticipantEvent
import com.toybeth.dokto.twilio.data.sdk.RoomManager
import com.toybeth.dokto.twilio.data.sdk.VideoTrackViewState
import com.toybeth.dokto.twilio.ui.call.*
import com.twilio.audioswitch.AudioSwitch
import com.twilio.video.Participant
import dagger.hilt.android.lifecycle.HiltViewModel
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import io.uniflow.core.flow.onState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwilioCallViewModel @Inject constructor(
    private val roomManager: RoomManager,
    private val audioSwitch: AudioSwitch,
    private val permissionUtil: PermissionUtil,
    private val participantManager: ParticipantManager = ParticipantManager(),
    initialViewState: RoomViewState = RoomViewState(participantManager.primaryParticipant)
) : AndroidDataFlow(defaultState = initialViewState) {

    private var permissionCheckRetry = false

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var roomManagerJob: Job? = null

    init {
        audioSwitch.start { audioDevices, selectedDevice ->
            updateState { currentState ->
                currentState.copy(
                    selectedDevice = selectedDevice,
                    availableAudioDevices = audioDevices
                )
            }
        }
        subscribeToRoomEvents()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public override fun onCleared() {
        super.onCleared()
        audioSwitch.stop()
        roomManagerJob?.cancel()
    }

    fun processInput(viewEvent: RoomViewEvent) {
        Logger.d("View Event: $viewEvent")

        when (viewEvent) {
            RoomViewEvent.OnResume -> checkPermissions()
            RoomViewEvent.OnPause -> roomManager.onPause()
            RoomViewEvent.OnDestroy -> roomManager.onDestroy()
            is RoomViewEvent.SelectAudioDevice -> {
                audioSwitch.selectDevice(viewEvent.device)
            }
            RoomViewEvent.ActivateAudioDevice -> {
                audioSwitch.activate()
            }
            RoomViewEvent.DeactivateAudioDevice -> {
                audioSwitch.deactivate()
            }
            is RoomViewEvent.Connect -> {
                connect(viewEvent.identity, viewEvent.roomName)
            }
            is RoomViewEvent.PinParticipant -> {
                participantManager.changePinnedParticipant(viewEvent.sid)
                updateParticipantViewState()
            }
            RoomViewEvent.ToggleLocalVideo -> roomManager.toggleLocalVideo()
            RoomViewEvent.EnableLocalVideo -> roomManager.enableLocalVideo()
            RoomViewEvent.DisableLocalVideo -> roomManager.disableLocalVideo()
            RoomViewEvent.ToggleLocalAudio -> roomManager.toggleLocalAudio()
            RoomViewEvent.EnableLocalAudio -> roomManager.enableLocalAudio()
            RoomViewEvent.DisableLocalAudio -> roomManager.disableLocalAudio()
            is RoomViewEvent.StartScreenCapture -> roomManager.startScreenCapture(
                viewEvent.captureResultCode, viewEvent.captureIntent
            )
            RoomViewEvent.StopScreenCapture -> roomManager.stopScreenCapture()
            RoomViewEvent.SwitchCamera -> roomManager.switchCamera()
            is RoomViewEvent.VideoTrackRemoved -> {
                participantManager.updateParticipantVideoTrack(viewEvent.sid, null)
                updateParticipantViewState()
            }
            is RoomViewEvent.ScreenTrackRemoved -> {
                participantManager.updateParticipantScreenTrack(viewEvent.sid, null)
                updateParticipantViewState()
            }
            RoomViewEvent.Disconnect -> roomManager.disconnect()
        }
    }

    private fun subscribeToRoomEvents() {
        roomManager.roomEvents.let { sharedFlow ->
            roomManagerJob = viewModelScope.launch {
                Logger.d("Listening for RoomEvents")
                sharedFlow.collect { observeRoomEvents(it) }
            }
        }
    }

    private fun checkPermissions() {
        val isCameraEnabled = permissionUtil.isPermissionGranted(Manifest.permission.CAMERA)
        val isMicEnabled = permissionUtil.isPermissionGranted(Manifest.permission.RECORD_AUDIO)

        updateState { currentState ->
            currentState.copy(isCameraEnabled = isCameraEnabled, isMicEnabled = isMicEnabled)
        }
        if (isCameraEnabled && isMicEnabled) {
            roomManager.onResume()
        } else {
            if (!permissionCheckRetry) {
                action {
                    sendEvent {
                        permissionCheckRetry = true
                        RoomViewEffect.PermissionsDenied
                    }
                }
            }
        }
    }

    private fun observeRoomEvents(roomEvent: RoomEvent) {
        Logger.d("observeRoomEvents: %s", roomEvent)
        when (roomEvent) {
            is RoomEvent.Connecting -> {
                showConnectingViewState()
            }
            is RoomEvent.Connected -> {
                showConnectedViewState(roomEvent.roomName)
                checkParticipants(roomEvent.participants)
                action { sendEvent { RoomViewEffect.Connected(roomEvent.room) } }
            }
            is RoomEvent.Disconnected -> showLobbyViewState()
            is RoomEvent.DominantSpeakerChanged -> {
                participantManager.changeDominantSpeaker(roomEvent.newDominantSpeakerSid)
                updateParticipantViewState()
            }
            is RoomEvent.ConnectFailure -> action {
                sendEvent {
                    showLobbyViewState()
                    RoomViewEffect.ShowConnectFailureDialog
                }
            }
            is RoomEvent.MaxParticipantFailure -> action {
                sendEvent { RoomViewEffect.ShowMaxParticipantFailureDialog }
                showLobbyViewState()
            }
//            is RoomEvent.TokenError -> action {
//                sendEvent {
//                    showLobbyViewState()
//                    ShowTokenErrorDialog(roomEvent.serviceError)
//                }
//            }
            RoomEvent.RecordingStarted -> updateState { currentState ->
                currentState.copy(
                    isRecording = true
                )
            }
            RoomEvent.RecordingStopped -> updateState { currentState ->
                currentState.copy(
                    isRecording = false
                )
            }
            is RoomEvent.RemoteParticipantEvent -> handleRemoteParticipantEvent(roomEvent)
            is RoomEvent.LocalParticipantEvent -> handleLocalParticipantEvent(roomEvent)
            is RoomEvent.StatsUpdate -> updateState { currentState -> currentState.copy(roomStats = roomEvent.roomStats) }
        }
    }

    private fun handleRemoteParticipantEvent(remoteParticipantEvent: RemoteParticipantEvent) {
        when (remoteParticipantEvent) {
            is RemoteParticipantEvent.RemoteParticipantConnected -> addParticipant(
                remoteParticipantEvent.participant
            )
            is RemoteParticipantEvent.VideoTrackUpdated -> {
                participantManager.updateParticipantVideoTrack(remoteParticipantEvent.sid,
                    remoteParticipantEvent.videoTrack?.let { VideoTrackViewState(it) })
                updateParticipantViewState()
            }
            is RemoteParticipantEvent.TrackSwitchOff -> {
                participantManager.updateParticipantVideoTrack(
                    remoteParticipantEvent.sid,
                    VideoTrackViewState(
                        remoteParticipantEvent.videoTrack,
                        remoteParticipantEvent.switchOff
                    )
                )
                updateParticipantViewState()
            }
            is RemoteParticipantEvent.ScreenTrackUpdated -> {
                participantManager.updateParticipantScreenTrack(remoteParticipantEvent.sid,
                    remoteParticipantEvent.screenTrack?.let { VideoTrackViewState(it) })
                updateParticipantViewState()
            }
            is RemoteParticipantEvent.MuteRemoteParticipant -> {
                participantManager.muteParticipant(
                    remoteParticipantEvent.sid,
                    remoteParticipantEvent.mute
                )
                updateParticipantViewState()
            }
            is RemoteParticipantEvent.NetworkQualityLevelChange -> {
                participantManager.updateNetworkQuality(
                    remoteParticipantEvent.sid,
                    remoteParticipantEvent.networkQualityLevel
                )
                updateParticipantViewState()
            }
            is RemoteParticipantEvent.RemoteParticipantDisconnected -> {
                participantManager.removeParticipant(remoteParticipantEvent.sid)
                updateParticipantViewState()
            }
        }
    }

    private fun handleLocalParticipantEvent(localParticipantEvent: LocalParticipantEvent) {
        when (localParticipantEvent) {
            is LocalParticipantEvent.VideoTrackUpdated -> {
                participantManager.updateLocalParticipantVideoTrack(
                    localParticipantEvent.videoTrack?.let { VideoTrackViewState(it) })
                updateParticipantViewState()
                updateState { currentState -> currentState.copy(isVideoOff = localParticipantEvent.videoTrack == null) }
            }
            LocalParticipantEvent.AudioOn -> updateState { currentState ->
                currentState.copy(
                    isAudioMuted = false
                )
            }
            LocalParticipantEvent.AudioOff -> updateState { currentState ->
                currentState.copy(
                    isAudioMuted = true
                )
            }
            LocalParticipantEvent.AudioEnabled -> updateState { currentState ->
                currentState.copy(
                    isAudioEnabled = true
                )
            }
            LocalParticipantEvent.AudioDisabled -> updateState { currentState ->
                currentState.copy(
                    isAudioEnabled = false
                )
            }
            LocalParticipantEvent.ScreenCaptureOn -> updateState { currentState ->
                currentState.copy(
                    isScreenCaptureOn = true
                )
            }
            LocalParticipantEvent.ScreenCaptureOff -> updateState { currentState ->
                currentState.copy(
                    isScreenCaptureOn = false
                )
            }
            LocalParticipantEvent.VideoEnabled -> updateState { currentState ->
                currentState.copy(
                    isVideoEnabled = true
                )
            }
            LocalParticipantEvent.VideoDisabled -> updateState { currentState ->
                currentState.copy(
                    isVideoEnabled = false
                )
            }
        }
    }

    private fun addParticipant(participant: Participant) {
        val participantViewState = buildParticipantViewState(participant)
        participantManager.addParticipant(participantViewState)
        updateParticipantViewState()
    }

    private fun showLobbyViewState() {
        action { sendEvent { RoomViewEffect.Disconnected } }
        updateState { currentState ->
            currentState.copy(configuration = RoomViewConfiguration.Lobby)
        }
        participantManager.clearRemoteParticipants()
        updateParticipantViewState()
    }

    private fun showConnectingViewState() {
        updateState { currentState ->
            currentState.copy(configuration = RoomViewConfiguration.Connecting)
        }
    }

    private fun showConnectedViewState(roomName: String) {
        updateState { currentState ->
            currentState.copy(configuration = RoomViewConfiguration.Connected, title = roomName)
        }
    }

    private fun checkParticipants(participants: List<Participant>) {
        for ((index, participant) in participants.withIndex()) {
            if (index == 0) { // local participant
                participantManager.updateLocalParticipantSid(participant.sid)
            } else {
                participantManager.addParticipant(buildParticipantViewState(participant))
            }
        }
        updateParticipantViewState()
    }

    private fun updateParticipantViewState() {
        updateState { currentState ->
            currentState.copy(
                participantThumbnails = participantManager.participantThumbnails,
                primaryParticipant = participantManager.primaryParticipant
            )
        }
    }

    private fun connect(identity: String, roomName: String) =
        viewModelScope.launch {
            roomManager.connect(
                identity,
                roomName
            )
        }

    private fun updateState(action: (currentState: RoomViewState) -> UIState) =
        action { onState<RoomViewState> { currentState -> setState { action(currentState) } } }
}
