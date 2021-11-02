package com.toybeth.dokto.twilio.ui.call

import com.twilio.video.BaseTrackStats

class StatsListItem private constructor(builder: Builder) {
    val trackSid: String?
    val trackName: String?
    val codec: String?
    val packetsLost: Int
    val bytes: Long
    val rtt: Long
    val dimensions: String?
    val framerate: Int
    val jitter: Int
    val audioLevel: Int
    val isLocalTrack: Boolean
    val isAudioTrack: Boolean

    class Builder {
        internal var trackSid: String? = null
        internal var trackName: String? = null
        internal var codec: String? = null
        internal var packetsLost = 0
        internal var bytes: Long = 0
        internal var rtt: Long = 0
        internal var dimensions: String? = null
        internal var framerate = 0
        internal var jitter = 0
        internal var audioLevel = 0
        internal var isLocalTrack = false
        internal var isAudioTrack = false
        fun trackName(trackName: String?): Builder {
            this.trackName = trackName
            return this
        }

        fun bytes(bytes: Long): Builder {
            this.bytes = bytes
            return this
        }

        fun rtt(rtt: Long): Builder {
            this.rtt = rtt
            return this
        }

        fun dimensions(dimensions: String?): Builder {
            this.dimensions = dimensions
            return this
        }

        fun framerate(framerate: Int): Builder {
            this.framerate = framerate
            return this
        }

        fun jitter(jitter: Int): Builder {
            this.jitter = jitter
            return this
        }

        fun audioLevel(audioLevel: Int): Builder {
            this.audioLevel = audioLevel
            return this
        }

        fun isLocalTrack(isLocalTrack: Boolean): Builder {
            this.isLocalTrack = isLocalTrack
            return this
        }

        fun isAudioTrack(isAudioTrack: Boolean): Builder {
            this.isAudioTrack = isAudioTrack
            return this
        }

        fun baseTrackInfo(trackStats: BaseTrackStats): Builder {
            codec = trackStats.codec
            packetsLost = trackStats.packetsLost
            trackSid = trackStats.trackSid
            return this
        }

        fun build(): StatsListItem {
            return StatsListItem(this)
        }
    }

    init {
        trackSid = builder.trackSid
        trackName = builder.trackName
        codec = builder.codec
        packetsLost = builder.packetsLost
        bytes = builder.bytes
        rtt = builder.rtt
        dimensions = builder.dimensions
        framerate = builder.framerate
        jitter = builder.jitter
        audioLevel = builder.audioLevel
        isLocalTrack = builder.isLocalTrack
        isAudioTrack = builder.isAudioTrack
    }
}