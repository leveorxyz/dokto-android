package com.toybethsystems.dokto.twilio.data.sdk

import android.content.Context
import com.toybethsystems.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybethsystems.dokto.twilio.data.rest.TwilioAuthDataSource
import com.twilio.video.*
import com.twilio.video.ktx.createBandwidthProfileOptions
import com.twilio.video.ktx.createConnectOptions
import tvi.webrtc.voiceengine.WebRtcAudioManager
import tvi.webrtc.voiceengine.WebRtcAudioUtils

class ConnectOptionsFactory(
    private val sharedPreferences: TwilioSharedPreference,
    private val tokenService: TwilioAuthDataSource
) {

    suspend fun newInstance(identity: String, roomName: String): ConnectOptions {

        val token = tokenService.getToken(identity, roomName)
        val enableInsights = true

        val enableAutomaticTrackSubscription = true

        val enableDominantSpeaker = true

        val preferedVideoCodec: VideoCodec = getVideoCodecPreference()

        val preferredAudioCodec: AudioCodec = getAudioCodecPreference()

        val configuration = NetworkQualityConfiguration(
            NetworkQualityVerbosity.NETWORK_QUALITY_VERBOSITY_MINIMAL,
            NetworkQualityVerbosity.NETWORK_QUALITY_VERBOSITY_MINIMAL
        )

        val mode = getBandwidthProfileMode(BandwidthProfileMode.COLLABORATION.name)
        val maxSubscriptionBitrate =
            TwilioSharedPreference.BANDWIDTH_PROFILE_MAX_SUBSCRIPTION_BITRATE_DEFAULT.toLong()

        val dominantSpeakerPriority =
            getDominantSpeakerPriority(TwilioSharedPreference.BANDWIDTH_PROFILE_DOMINANT_SPEAKER_PRIORITY_DEFAULT)
        val trackSwitchOffMode =
            getTrackSwitchOffMode(TwilioSharedPreference.BANDWIDTH_PROFILE_TRACK_SWITCH_OFF_MODE_DEFAULT)
        val clientTrackSwitchOffControl =
            getClientTrackSwitchOffControl(TwilioSharedPreference.BANDWIDTH_PROFILE_TRACK_SWITCH_OFF_MODE_DEFAULT)
        val videoContentPreferencesMode =
            getVideoContentPreferencesMode(TwilioSharedPreference.BANDWIDTH_PROFILE_VIDEO_CONTENT_PREFERENCES_MODE_DEFAULT)
        val bandwidthProfileOptions = createBandwidthProfileOptions {
            mode(mode)
            maxSubscriptionBitrate(maxSubscriptionBitrate)
            dominantSpeakerPriority(dominantSpeakerPriority)
            trackSwitchOffMode(trackSwitchOffMode)
            clientTrackSwitchOffControl?.let { clientTrackSwitchOffControl(it) }
            videoContentPreferencesMode?.let { videoContentPreferencesMode(it) }
        }

        val acousticEchoCanceler = TwilioSharedPreference.AUDIO_ACOUSTIC_ECHO_CANCELER_DEFAULT
        val noiseSuppressor = TwilioSharedPreference.AUDIO_ACOUSTIC_NOISE_SUPRESSOR_DEFAULT
        val automaticGainControl = TwilioSharedPreference.AUDIO_AUTOMATIC_GAIN_CONTROL_DEFAULT
        val openSLESUsage = TwilioSharedPreference.AUDIO_OPEN_SLES_USAGE_DEFAULT
        WebRtcAudioUtils.setWebRtcBasedAcousticEchoCanceler(!acousticEchoCanceler)
        WebRtcAudioUtils.setWebRtcBasedNoiseSuppressor(!noiseSuppressor)
        WebRtcAudioUtils.setWebRtcBasedAutomaticGainControl(!automaticGainControl)
        WebRtcAudioManager.setBlacklistDeviceForOpenSLESUsage(!openSLESUsage)

        val isNetworkQualityEnabled = TwilioSharedPreference.ENABLE_NETWORK_QUALITY_LEVEL_DEFAULT

        val maxVideoBitrate = TwilioSharedPreference.MAX_VIDEO_BITRATE_DEFAULT

        val maxAudioBitrate = TwilioSharedPreference.MAX_AUDIO_BITRATE_DEFAULT

        return createConnectOptions(token) {
            roomName(roomName)
            enableInsights(enableInsights)
            enableAutomaticSubscription(enableAutomaticTrackSubscription)
            enableDominantSpeaker(enableDominantSpeaker)
            enableNetworkQuality(isNetworkQualityEnabled)
            networkQualityConfiguration(configuration)
            bandwidthProfile(bandwidthProfileOptions)
            encodingParameters(EncodingParameters(maxAudioBitrate, maxVideoBitrate))
            preferVideoCodecs(listOf(preferedVideoCodec))
            preferAudioCodecs(listOf(preferredAudioCodec))
        }
    }

    private fun getTrackSwitchOffMode(trackSwitchOffModeString: String) =
        when (trackSwitchOffModeString) {
            TrackSwitchOffMode.PREDICTED.name -> TrackSwitchOffMode.PREDICTED
            TrackSwitchOffMode.DETECTED.name -> TrackSwitchOffMode.DETECTED
            TrackSwitchOffMode.DISABLED.name -> TrackSwitchOffMode.DISABLED
            else -> null
        }

    private fun getClientTrackSwitchOffControl(controlString: String) =
        when (controlString.uppercase()) {
            ClientTrackSwitchOffControl.MANUAL.name -> ClientTrackSwitchOffControl.MANUAL
            ClientTrackSwitchOffControl.AUTO.name -> ClientTrackSwitchOffControl.AUTO
            else -> null
        }

    private fun getVideoContentPreferencesMode(modeString: String) =
        when (modeString.uppercase()) {
            VideoContentPreferencesMode.MANUAL.name -> VideoContentPreferencesMode.MANUAL
            VideoContentPreferencesMode.AUTO.name -> VideoContentPreferencesMode.AUTO
            else -> null
        }

    private fun getDominantSpeakerPriority(dominantSpeakerPriorityString: String) =
        when (dominantSpeakerPriorityString) {
            TrackPriority.LOW.name -> TrackPriority.LOW
            TrackPriority.STANDARD.name -> TrackPriority.STANDARD
            TrackPriority.HIGH.name -> TrackPriority.HIGH
            else -> null
        }

    private fun getBandwidthProfileMode(modeString: String): BandwidthProfileMode? {
        return when (modeString) {
            BandwidthProfileMode.COLLABORATION.name -> BandwidthProfileMode.COLLABORATION
            BandwidthProfileMode.GRID.name -> BandwidthProfileMode.GRID
            BandwidthProfileMode.PRESENTATION.name -> BandwidthProfileMode.PRESENTATION
            else -> null
        }
    }

    private fun getVideoCodecPreference(): VideoCodec {
        return sharedPreferences.videoCodec.let { videoCodecName ->
            when (videoCodecName) {
                Vp8Codec.NAME -> {
                    val simulcast = sharedPreferences.vp8SimulCast
                    Vp8Codec(simulcast)
                }
                H264Codec.NAME -> H264Codec()
                Vp9Codec.NAME -> Vp9Codec()
                else -> Vp8Codec()
            }
        } ?: Vp8Codec()
    }

    private fun getAudioCodecPreference(): AudioCodec {
        return TwilioSharedPreference.AUDIO_CODEC_DEFAULT.let { audioCodecName ->

            when (audioCodecName) {
                IsacCodec.NAME -> IsacCodec()
                PcmaCodec.NAME -> PcmaCodec()
                PcmuCodec.NAME -> PcmuCodec()
                G722Codec.NAME -> G722Codec()
                else -> OpusCodec()
            }
        } ?: OpusCodec()
    }
}
