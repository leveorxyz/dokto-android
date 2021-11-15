package com.toybethsystems.dokto.twilio.ui.call

import android.annotation.TargetApi
import android.content.Context
import android.widget.FrameLayout
import com.toybethsystems.dokto.twilio.ui.call.ParticipantView
import androidx.constraintlayout.widget.ConstraintLayout
import com.twilio.video.VideoTextureView
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import com.twilio.video.VideoScaleType
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntDef
import com.toybethsystems.dokto.twilio.R
import com.twilio.video.VideoTrack
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

abstract class ParticipantView : FrameLayout {
    var identity = ""
        set(value) {
            videoIdentity?.text = value
            selectedIdentity?.text = value
            field = value
        }
    open var state = State.NO_VIDEO
    set(value) {
        field = value
        when (value) {
            State.SWITCHED_OFF, State.VIDEO -> videoState()
            State.NO_VIDEO, State.SELECTED -> {
                videoLayout?.visibility = GONE
                videoIdentity?.visibility = GONE
                videoView?.visibility = GONE
                selectedLayout?.visibility = VISIBLE
                stubImage?.visibility = VISIBLE
                selectedIdentity?.visibility = VISIBLE
            }
            else -> {
            }
        }
    }
    var mirror = false
    set(value) {
        field = value
        videoView?.mirror = this.mirror
    }
    var scaleType = DEFAULT_VIDEO_SCALE_TYPE.ordinal
    set(value) {
        field = scaleType
        videoView?.videoScaleType = VideoScaleType.values()[this.scaleType]
    }
    var videoTrack: VideoTrack? = null
    var videoLayout: ConstraintLayout? = null
    var videoIdentity: TextView? = null
    var videoView: VideoTextureView? = null
    var selectedLayout: RelativeLayout? = null
    var stubImage: ImageView? = null
    var networkQualityLevelImg: ImageView? = null
    var selectedIdentity: TextView? = null
    var audioToggle: ImageView? = null
    var pinImage: ImageView? = null

    constructor(context: Context) : super(context) {
        initParams(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initParams(context, attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initParams(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initParams(context, attrs)
    }

    private fun videoState() {
        selectedLayout!!.visibility = GONE
        stubImage!!.visibility = GONE
        selectedIdentity!!.visibility = GONE
        videoLayout!!.visibility = VISIBLE
        videoIdentity!!.visibility = VISIBLE
        videoView!!.visibility = VISIBLE
    }

    fun setMuted(muted: Boolean) {
        if (audioToggle != null) audioToggle!!.visibility = if (muted) VISIBLE else GONE
    }

    fun setPinned(pinned: Boolean) {
        if (pinImage != null) pinImage!!.visibility = if (pinned) VISIBLE else GONE
    }

    fun initParams(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val stylables = context.theme
                .obtainStyledAttributes(attrs, R.styleable.ParticipantView, 0, 0)

            // obtain identity
            val identityResId = stylables.getResourceId(R.styleable.ParticipantView_identity, -1)
            identity = if (identityResId != -1) context.getString(identityResId) else ""

            // obtain state
            state = stylables.getInt(
                R.styleable.ParticipantView_state, State.NO_VIDEO
            )

            // obtain mirror
            mirror = stylables.getBoolean(R.styleable.ParticipantView_mirror, false)

            // obtain scale type
            scaleType = stylables.getInt(
                R.styleable.ParticipantView_type, DEFAULT_VIDEO_SCALE_TYPE.ordinal
            )
            stylables.recycle()
        }
    }

    @kotlin.annotation.Retention(
        AnnotationRetention.SOURCE
    )
    annotation class State {
        companion object {
            var VIDEO = 0
            var NO_VIDEO = 1
            var SELECTED = 2
            var SWITCHED_OFF = 3
        }
    }

    companion object {
        private val DEFAULT_VIDEO_SCALE_TYPE = VideoScaleType.ASPECT_FIT
    }
}