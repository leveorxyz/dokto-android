package com.toybethsystems.dokto.twilio.data.sdk

import com.orhanobut.logger.Logger
import com.twilio.video.*

class LocalParticipantListener(private val roomManager: RoomManager) : LocalParticipant.Listener {

    override fun onNetworkQualityLevelChanged(
        localParticipant: LocalParticipant,
        networkQualityLevel: NetworkQualityLevel
    ) {
        Logger.d(
            "LocalParticipant NetworkQualityLevel changed for LocalParticipant sid: %s, NetworkQualityLevel: %s",
            localParticipant.sid, networkQualityLevel
        )

        roomManager.sendRoomEvent(
            RoomEvent.RemoteParticipantEvent.NetworkQualityLevelChange(
                localParticipant.sid,
                networkQualityLevel
            )
        )
    }

    override fun onVideoTrackPublished(
        localParticipant: LocalParticipant,
        localVideoTrackPublication: LocalVideoTrackPublication
    ) {
    }

    override fun onVideoTrackPublicationFailed(
        localParticipant: LocalParticipant,
        localVideoTrack: LocalVideoTrack,
        twilioException: TwilioException
    ) {
    }

    override fun onDataTrackPublished(
        localParticipant: LocalParticipant,
        localDataTrackPublication: LocalDataTrackPublication
    ) {
    }

    override fun onDataTrackPublicationFailed(
        localParticipant: LocalParticipant,
        localDataTrack: LocalDataTrack,
        twilioException: TwilioException
    ) {
    }

    override fun onAudioTrackPublished(
        localParticipant: LocalParticipant,
        localAudioTrackPublication: LocalAudioTrackPublication
    ) {
    }

    override fun onAudioTrackPublicationFailed(
        localParticipant: LocalParticipant,
        localAudioTrack: LocalAudioTrack,
        twilioException: TwilioException
    ) {
    }
}
