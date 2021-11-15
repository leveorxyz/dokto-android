package com.toybethsystems.dokto.twilio.ui.call

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.toybethsystems.dokto.twilio.R
import com.toybethsystems.dokto.twilio.databinding.ParticipantViewBinding

class ParticipantThumbView : ParticipantView {
    private var binding: ParticipantViewBinding? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        binding = ParticipantViewBinding.inflate(LayoutInflater.from(context), this, true)
        videoLayout = binding!!.videoLayout
        videoIdentity = binding!!.videoIdentity
        videoView = binding!!.video
        selectedLayout = binding!!.selectedLayout
        stubImage = binding!!.stub
        networkQualityLevelImg = binding!!.networkQuality
        selectedIdentity = binding!!.selectedIdentity
        audioToggle = binding!!.audioToggle
        pinImage = binding!!.pin
        identity = identity
        state = state
        mirror = mirror
        scaleType = scaleType
    }

    override var state: Int
        get() = super.state
        set(state) {
            super.state = state
            binding!!.participantTrackSwitchOffBackground.visibility = isSwitchOffViewVisible(state)
            binding!!.participantTrackSwitchOffIcon.visibility = isSwitchOffViewVisible(state)
            var resId = R.drawable.participant_background
            if (state == State.SELECTED) {
                resId = R.drawable.participant_selected_background
            }
            selectedLayout!!.background = ContextCompat.getDrawable(context, resId)
        }

    private fun isSwitchOffViewVisible(state: Int): Int {
        return if (state == State.SWITCHED_OFF) VISIBLE else GONE
    }
}