package com.toybethsystems.dokto.twilio.data.sdk

import com.twilio.video.VideoTrack

data class VideoTrackViewState constructor (
    val videoTrack: VideoTrack,
    val isSwitchedOff: Boolean = false
)
