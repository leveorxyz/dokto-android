package com.toybeth.dokto.twilio

import android.R
import android.widget.ProgressBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.twilio.video.VideoView

@Preview
@Composable
fun twilioCallVideoView(
    videoView: VideoView,
    backCameraEnabled: MutableState<Boolean>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { _ ->
                    videoView.apply {
                        mirror = backCameraEnabled.value
                    }
                }
            )
            AndroidView(
                modifier = Modifier.wrapContentSize(),
                factory = { context ->
                    ProgressBar(context, null, 0, R.attr.progressBarStyleLarge).apply {
                        isIndeterminate = true
                    }
                }
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = ""
                )
            }
        }
    }
}