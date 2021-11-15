package com.toybethsystems.dokto.base.utils.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.toybethsystems.dokto.base.R
import com.toybethsystems.dokto.base.theme.DoktoTheme

fun ComposeView.setContentView(content: @Composable () -> Unit) {
    setContent {
        DoktoTheme(
            content = content
        )
    }
}