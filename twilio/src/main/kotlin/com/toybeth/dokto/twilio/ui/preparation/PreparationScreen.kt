package com.toybeth.dokto.twilio.ui.preparation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.toybeth.dokto.twilio.ui.common.TwilioCallViewModel
import com.toybeth.dokto.twilio.ui.common.components.Fab
import com.toybeth.dokto.twilio.ui.common.components.TwilioVideo

@Composable
fun PreparationScreen(
    onJoinRoomClick: () -> Unit,
    preparationViewModel: TwilioCallViewModel = viewModel()
) {

    val localVideoTrack = preparationViewModel.localVideoTrackLiveData.observeAsState()
    val videoEnabled = preparationViewModel.videoEnabled.observeAsState()
    val audioEnabled = preparationViewModel.audioEnabled.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        TwilioVideo(modifier = Modifier.fillMaxSize()) { videoView ->
            localVideoTrack.value?.let { track ->
                if (track.isEnabled) {
                    track.addSink(videoView)
                }
            }
        }

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(256.dp)
        )

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
                    onClick = { preparationViewModel.toggleLocalVideoStream() }
                )
            } else {
                Fab(
                    backgroundColor = Color.LightGray,
                    icon = Icons.Filled.VideocamOff,
                    iconTint = Color.White,
                    onClick = { preparationViewModel.toggleLocalVideoStream() }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Join Room")
                },
                onClick = { onJoinRoomClick() },
                modifier = Modifier.height(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            if (audioEnabled.value == true) {
                Fab(
                    backgroundColor = Color.White,
                    icon = Icons.Filled.Mic,
                    iconTint = Color.DarkGray,
                    onClick = { preparationViewModel.toggleLocalAudioStream() }
                )
            } else {
                Fab(
                    backgroundColor = Color.LightGray,
                    icon = Icons.Filled.MicOff,
                    iconTint = Color.White,
                    onClick = { preparationViewModel.toggleLocalAudioStream() }
                )
            }
        }
    }
}