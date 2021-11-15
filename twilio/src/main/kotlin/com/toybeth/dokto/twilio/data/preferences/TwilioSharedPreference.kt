package com.toybeth.dokto.twilio.data.preferences

import android.content.Context
import com.google.gson.Gson
import com.toybethsystems.dokto.base.R
import com.twilio.video.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TwilioSharedPreference @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val MESSAGE = "message"
        const val INTERNAL = "pref_internal"
        const val SERVER_DEFAULT = "Server Default"
        const val EMAIL = "pref_email"
        const val DISPLAY_NAME = "pref_display_name"
        const val ENVIRONMENT = "pref_environment"
        const val TOPOLOGY = "pref_topology"
        const val QCIF_VIDEO_WIDTH = 176
        const val QCIF_VIDEO_HEIGHT = 144
        val VIDEO_DIMENSIONS = arrayOf(
            VideoDimensions(QCIF_VIDEO_WIDTH, QCIF_VIDEO_HEIGHT),
            VideoDimensions.CIF_VIDEO_DIMENSIONS,
            VideoDimensions.VGA_VIDEO_DIMENSIONS,
            VideoDimensions.WVGA_VIDEO_DIMENSIONS,
            VideoDimensions.HD_540P_VIDEO_DIMENSIONS,
            VideoDimensions.HD_720P_VIDEO_DIMENSIONS,
            VideoDimensions.HD_960P_VIDEO_DIMENSIONS,
            VideoDimensions.HD_S1080P_VIDEO_DIMENSIONS,
            VideoDimensions.HD_1080P_VIDEO_DIMENSIONS
        )
        const val VIDEO_CAPTURE_RESOLUTION = "pref_video_capture_resolution"
        const val VIDEO_CAPTURE_RESOLUTION_DEFAULT = "1"
        const val VERSION_NAME = "pref_version_name"
        const val VIDEO_LIBRARY_VERSION = "pref_video_library_version"
        const val LOGOUT = "pref_logout"
        const val ENABLE_STATS = "pref_enable_stats"
        const val ENABLE_STATS_DEFAULT = true
        const val ENABLE_INSIGHTS = "pref_enable_insights"
        const val ENABLE_NETWORK_QUALITY_LEVEL = "pref_enable_network_quality_level"
        const val ENABLE_NETWORK_QUALITY_LEVEL_DEFAULT = true
        const val ENABLE_AUTOMATIC_TRACK_SUBSCRIPTION = "pref_enable_automatic_subscription"
        const val ENABLE_AUTOMATIC_TRACK_SUBSCRIPTION_DEFAULT = true
        const val ENABLE_DOMINANT_SPEAKER = "pref_enable_dominant_speaker"
        const val ENABLE_DOMINANT_SPEAKER_DEFAULT = true
        const val ENABLE_INSIGHTS_DEFAULT = true
        const val VIDEO_CODEC = "pref_video_codecs"
        const val VIDEO_CODEC_DEFAULT = Vp8Codec.NAME
        const val VP8_SIMULCAST = "pref_vp8_simulcast"
        const val VP8_SIMULCAST_DEFAULT = false
        const val AUDIO_CODEC = "pref_audio_codecs"
        const val AUDIO_CODEC_DEFAULT = OpusCodec.NAME
        const val MAX_AUDIO_BITRATE = "pref_max_audio_bitrate"
        const val MAX_AUDIO_BITRATE_DEFAULT = 16
        const val MAX_VIDEO_BITRATE = "pref_max_video_bitrate"
        const val MAX_VIDEO_BITRATE_DEFAULT = 0
        const val RECORD_PARTICIPANTS_ON_CONNECT = "pref_record_participants_on_connect"
        const val RECORD_PARTICIPANTS_ON_CONNECT_DEFAULT = false
        const val BANDWIDTH_PROFILE_MODE = "pref_bandwidth_profile_mode"
        val BANDWIDTH_PROFILE_MODE_DEFAULT = BandwidthProfileMode.COLLABORATION.name
        const val BANDWIDTH_PROFILE_MAX_SUBSCRIPTION_BITRATE = "pref_bandwidth_profile_max_subscription_bitrate"
        const val BANDWIDTH_PROFILE_MAX_SUBSCRIPTION_BITRATE_DEFAULT = 2400
        const val BANDWIDTH_PROFILE_DOMINANT_SPEAKER_PRIORITY = "pref_bandwidth_profile_dominant_speaker_priority"
        val BANDWIDTH_PROFILE_DOMINANT_SPEAKER_PRIORITY_DEFAULT = TrackPriority.STANDARD.name
        const val BANDWIDTH_PROFILE_TRACK_SWITCH_OFF_MODE = "pref_bandwidth_profile_track_switch_off_mode"
        const val BANDWIDTH_PROFILE_TRACK_SWITCH_OFF_MODE_DEFAULT = SERVER_DEFAULT
        const val BANDWIDTH_PROFILE_TRACK_SWITCH_OFF_CONTROL = "pref_bandwidth_profile_track_switch_off_control"
        const val BANDWIDTH_PROFILE_TRACK_SWITCH_OFF_CONTROL_DEFAULT = SERVER_DEFAULT
        const val BANDWIDTH_PROFILE_VIDEO_CONTENT_PREFERENCES_MODE = "pref_bandwidth_profile_video_content_preferences_mode"
        const val BANDWIDTH_PROFILE_VIDEO_CONTENT_PREFERENCES_MODE_DEFAULT = SERVER_DEFAULT
        const val AUDIO_ACOUSTIC_ECHO_CANCELER = "pref_audio_acoustic_echo_canceler"
        const val AUDIO_ACOUSTIC_ECHO_CANCELER_DEFAULT = true
        const val AUDIO_ACOUSTIC_NOISE_SUPRESSOR = "pref_noise_supressor"
        const val AUDIO_ACOUSTIC_NOISE_SUPRESSOR_DEFAULT = true
        const val AUDIO_AUTOMATIC_GAIN_CONTROL = "pref_audio_automatic_gain_control"
        const val AUDIO_AUTOMATIC_GAIN_CONTROL_DEFAULT = true
        const val AUDIO_OPEN_SLES_USAGE = "pref_audio_open_sles_usage"
        const val AUDIO_OPEN_SLES_USAGE_DEFAULT = false
    }

    private var preference =
        context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE)
    private var editor = preference.edit()

    var enableInsight: Boolean
        get() {
            return getBoolean(ENABLE_INSIGHTS, ENABLE_INSIGHTS_DEFAULT)
        }
        set(value) {
            saveBoolean(ENABLE_INSIGHTS, value)
        }

    var enableAutomaticTrackSubscription: Boolean
        get() {
            return getBoolean(ENABLE_DOMINANT_SPEAKER, ENABLE_DOMINANT_SPEAKER_DEFAULT)
        }
        set(value) {
            saveBoolean(ENABLE_DOMINANT_SPEAKER, value)
        }

    var enableDominantSpeaker: Boolean
        get() {
            return getBoolean(ENABLE_DOMINANT_SPEAKER, ENABLE_DOMINANT_SPEAKER_DEFAULT)
        }
        set(value) {
            saveBoolean(ENABLE_DOMINANT_SPEAKER, value)
        }

    var topology: String?
        get() {
            return getNullableString(TOPOLOGY, null)
        }
        set(value) {
            saveString(TOPOLOGY, value)
        }
    var videoCodec: String
        get() {
            return getString(VIDEO_CODEC, VIDEO_CODEC_DEFAULT)
        }
        set(value) {
            saveString(VIDEO_CODEC, value)
        }

    var vp8SimulCast: Boolean
        get() {
            return getBoolean(VP8_SIMULCAST, VP8_SIMULCAST_DEFAULT)
        }
        set(value) {
            saveBoolean(VP8_SIMULCAST, value)
        }

    var videoCaptureResolution: String
        get() {
            return getString(VIDEO_CAPTURE_RESOLUTION, VIDEO_CAPTURE_RESOLUTION_DEFAULT)
        }
        set(value) {
            saveString(VIDEO_CAPTURE_RESOLUTION, value)
        }

    private fun saveString(key: String, value: String?) {
        editor.putString(key, value).apply()
    }

    private fun getNullableString(key: String, defaultValue: String? = null): String? {
        return preference.getString(key, defaultValue)
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    private fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(key, defaultValue)
    }

    private fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }

    private fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        return preference.getFloat(key, defaultValue)
    }

    private fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    private fun saveObject(key: String, value: Any) {
        val valueString = Gson().toJson(value)
        saveString(key, valueString)
    }

    private fun <T> getObject(key: String, clazz: Class<T>): T {
        return Gson().fromJson<T>(preference.getString(key, "{}"), clazz)
    }
}