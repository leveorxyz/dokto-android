package com.toybethsystems.dokto.twilio.ui.call

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.toybethsystems.dokto.twilio.R
import com.toybethsystems.dokto.twilio.data.sdk.VideoTrackViewState
import com.twilio.video.NetworkQualityLevel
import com.twilio.video.VideoTrack

internal class ParticipantViewHolder(internal val thumb: ParticipantThumbView) :
        RecyclerView.ViewHolder(thumb) {

    private val localParticipantIdentity = thumb.context.getString(R.string.you)

    fun bind(participantViewState: ParticipantViewState, viewEventAction: (RoomViewEvent) -> Unit) {
        Logger.d("bind ParticipantViewHolder with data item: %s", participantViewState)
        Logger.d("thumb: %s", thumb)

        thumb.run {
            participantViewState.sid?.let { sid ->
                setOnClickListener {
                    viewEventAction(RoomViewEvent.PinParticipant(sid))
                }
            }
            val identity = if (participantViewState.isLocalParticipant)
                localParticipantIdentity else participantViewState.identity
            this.identity = identity ?: ""
            setMuted(participantViewState.isMuted)
            setPinned(participantViewState.isPinned)

            updateVideoTrack(participantViewState)

            networkQualityLevelImg?.let {
                setNetworkQualityLevelImage(it, participantViewState.networkQualityLevel)
            }
        }
    }

    private fun updateVideoTrack(participantViewState: ParticipantViewState) {
        thumb.run {
            val videoTrackViewState = participantViewState.videoTrack
            val newVideoTrack = videoTrackViewState?.let { it.videoTrack }
            if (videoTrack !== newVideoTrack) {
                removeSink(videoTrack, this)
                videoTrack = newVideoTrack
                videoTrack?.let { videoTrack ->
                    setVideoState(videoTrackViewState)
                    if (videoTrack.isEnabled && this.videoView != null) videoTrack.addSink(this.videoView!!)
                } ?: (ParticipantView.State.NO_VIDEO.also { state = it })
            } else {
                setVideoState(videoTrackViewState)
            }
        }
    }

    private fun ParticipantThumbView.setVideoState(videoTrackViewState: VideoTrackViewState?) {
        if (videoTrackViewState?.let { it.isSwitchedOff } == true) {
            state = ParticipantView.State.SWITCHED_OFF
        } else {
            videoTrackViewState?.videoTrack?.let { state = ParticipantView.State.VIDEO }
                    ?: (ParticipantView.State.NO_VIDEO.also { state = it })
        }
    }

    private fun removeSink(videoTrack: VideoTrack?, view: ParticipantView) {
        if (view.videoView != null && (videoTrack == null || !videoTrack.sinks.contains(view.videoView))) return
        videoTrack?.removeSink(view.videoView!!)
    }

    private fun setNetworkQualityLevelImage(
        networkQualityImage: ImageView,
        networkQualityLevel: NetworkQualityLevel?
    ) {
        when (networkQualityLevel) {
            NetworkQualityLevel.NETWORK_QUALITY_LEVEL_ZERO -> R.drawable.network_quality_level_0
            NetworkQualityLevel.NETWORK_QUALITY_LEVEL_ONE -> R.drawable.network_quality_level_1
            NetworkQualityLevel.NETWORK_QUALITY_LEVEL_TWO -> R.drawable.network_quality_level_2
            NetworkQualityLevel.NETWORK_QUALITY_LEVEL_THREE -> R.drawable.network_quality_level_3
            NetworkQualityLevel.NETWORK_QUALITY_LEVEL_FOUR -> R.drawable.network_quality_level_4
            NetworkQualityLevel.NETWORK_QUALITY_LEVEL_FIVE -> R.drawable.network_quality_level_5
            else -> null
        }?.let { image ->
            networkQualityImage.visibility = View.VISIBLE
            networkQualityImage.setImageResource(image)
        } ?: run { networkQualityImage.visibility = View.GONE }
    }
}