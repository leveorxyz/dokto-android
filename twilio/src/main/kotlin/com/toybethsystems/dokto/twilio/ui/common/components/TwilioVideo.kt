package com.toybethsystems.dokto.twilio.ui.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.twilio.video.VideoView

@Composable
fun TwilioVideo(
    modifier: Modifier = Modifier,
    update: (VideoView) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            VideoView(context).apply {
                mirror = true
            }
        },
        update = { videoView -> update(videoView) }
    )
}