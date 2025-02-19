package com.toybethsystems.dokto.base.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.toybethsystems.dokto.base.R

private val DarkColorPalette = darkColors(
    primary = DoktoPrimary,
    primaryVariant = DoktoPrimaryVariant,
    secondary = DoktoSecondary,
    error = DoktoError
)

private val LightColorPalette = lightColors(
    primary = DoktoPrimary,
    primaryVariant = DoktoPrimaryVariant,
    secondary = DoktoSecondary,
    error = DoktoError

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DoktoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(defaultFontFamily = FontFamily(Font(R.font.poppins_regular))),
        content = content,
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
    )
}