package com.toybethsystems.dokto.twilio.ui.call

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.toybethsystems.dokto.twilio.ui.common.TwilioCallViewModel
import com.toybethsystems.dokto.twilio.ui.common.components.Fab
import com.toybethsystems.dokto.twilio.ui.common.components.TwilioVideo

@ExperimentalAnimationApi
@Composable
fun TwilioCallScreen(
    onCallEnd: () -> Unit,
    twilioCallViewModel: TwilioCallViewModel = viewModel()
) {

    val localVideoTrack = twilioCallViewModel.localVideoTrackLiveData.observeAsState()
    val subscribedVideoTrack = twilioCallViewModel.subscribedVideoTrackLiveData.observeAsState()
    val unsubscribedVideoTrack = twilioCallViewModel.unsubscribedVideoTrackLiveData.observeAsState()
    val videoEnabled = twilioCallViewModel.videoEnabled.observeAsState()
    val audioEnabled = twilioCallViewModel.audioEnabled.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TwilioVideo(modifier = Modifier.fillMaxSize()) { videoView ->
            subscribedVideoTrack.value?.let { track ->
                if (track.isEnabled) {
                    track.addSink(videoView)
                }
                unsubscribedVideoTrack.value?.removeSink(videoView)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TwilioVideo(
                modifier = Modifier
                    .height(144.dp)
                    .width(92.dp)
                    .clip(RoundedCornerShape(16.dp)) // currently doesn't work
            ) { videoView ->
                localVideoTrack.value?.let { track ->
                    if (track.isEnabled) {
                        track.addSink(videoView)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(videoEnabled.value == true) {
                Fab(
                    backgroundColor = Color.White,
                    icon = Icons.Filled.SwitchCamera,
                    iconTint = Color.DarkGray,
                    onClick = { twilioCallViewModel.switchCamera() }
                )
            }
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (videoEnabled.value == true) {
                Fab(
                    backgroundColor = Color.White,
                    icon = Icons.Filled.Videocam,
                    iconTint = Color.DarkGray,
                    onClick = { twilioCallViewModel.toggleLocalVideoStream() }
                )
            } else {
                Fab(
                    backgroundColor = Color.LightGray,
                    icon = Icons.Filled.VideocamOff,
                    iconTint = Color.White,
                    onClick = { twilioCallViewModel.toggleLocalVideoStream() }
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Fab(
                modifier = Modifier
                    .height(64.dp)
                    .width(100.dp),
                backgroundColor = Color.Red,
                icon = Icons.Filled.Call,
                iconTint = Color.White,
                iconModifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                onClick = { onCallEnd() }
            )

            Spacer(modifier = Modifier.width(24.dp))

            if (audioEnabled.value == true) {
                Fab(
                    backgroundColor = Color.White,
                    icon = Icons.Filled.Mic,
                    iconTint = Color.DarkGray,
                    onClick = { twilioCallViewModel.toggleLocalAudioStream() }
                )
            } else {
                Fab(
                    backgroundColor = Color.LightGray,
                    icon = Icons.Filled.MicOff,
                    iconTint = Color.White,
                    onClick = { twilioCallViewModel.toggleLocalAudioStream() }
                )
            }
        }
    }
}