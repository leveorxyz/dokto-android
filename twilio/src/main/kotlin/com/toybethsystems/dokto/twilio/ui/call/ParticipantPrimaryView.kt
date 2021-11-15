package com.toybethsystems.dokto.twilio.ui.call

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.VISIBLE
import com.toybethsystems.dokto.twilio.databinding.ParticipantPrimaryViewBinding

class ParticipantPrimaryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ParticipantView(context, attrs, defStyleAttr) {

    private val binding: ParticipantPrimaryViewBinding =
            ParticipantPrimaryViewBinding.inflate(LayoutInflater.from(context), this, true)
    init {
        videoLayout = binding.videoLayout
        videoIdentity = binding.videoIdentity
        videoView = binding.video
        selectedLayout = binding.selectedLayout
        stubImage = binding.stub
        selectedIdentity = binding.selectedIdentity
//        setIdentity(identity)
//        setState(state)
//        setMirror(mirror)
//        setScaleType(scaleType)
    }

    fun showIdentityBadge(show: Boolean) {
        binding.videoIdentity.visibility = if (show) VISIBLE else GONE
    }
}