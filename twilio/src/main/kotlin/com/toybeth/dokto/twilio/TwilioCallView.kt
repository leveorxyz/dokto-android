package com.toybeth.dokto.twilio

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twilio.video.VideoView

@Preview
@Composable
fun twilioCallView(
    videoView: VideoView,
    backCameraEnabled: MutableState<Boolean>,
    switchCameraClickListener: () -> Unit,
    videoEnabled: MutableState<Boolean>,
    videoClickListener: () -> Unit,
    audioEnabled: MutableState<Boolean>,
    muteClickListener: () -> Unit,
    endCallClickListener: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        twilioCallVideoView(videoView, backCameraEnabled)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.End
            ) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    FloatingActionButton(
                        onClick = switchCameraClickListener
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Switch camera"
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    FloatingActionButton(
                        onClick = videoClickListener
                    ) {
                        if(videoEnabled.value) {
                            Icon(
                                imageVector = Icons.Filled.VideoCameraFront,
                                contentDescription = "Video enabled"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.VideoCameraFront,
                                contentDescription = "Video disabled"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    FloatingActionButton(
                        onClick = muteClickListener
                    ) {
                        if(audioEnabled.value) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Mic enabled"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.MicOff,
                                contentDescription = "Mic disabled"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    FloatingActionButton(
                        onClick = endCallClickListener
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CallEnd,
                            contentDescription = "End call"
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}