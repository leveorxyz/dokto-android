package com.toybeth.dokto.base.utils.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.toybeth.dokto.base.R

fun ComposeView.setContentView(content: @Composable () -> Unit) {
    setContent {
        MaterialTheme(
            typography = Typography(defaultFontFamily = FontFamily(Font(R.font.poppins_regular))),
            content = content
        )
    }
}