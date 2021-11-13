package com.toybeth.docto.base.utils.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.toybeth.docto.base.R
import com.toybeth.docto.base.theme.DoktoTheme

fun ComposeView.setContentView(content: @Composable () -> Unit) {
    setContent {
        DoktoTheme(
            content = content
        )
    }
}