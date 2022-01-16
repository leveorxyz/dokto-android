package com.toybethsystems.dokto.twilio.ui.common.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Fab(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: ImageVector,
    iconTint: Color,
    iconModifier: Modifier = Modifier,
    description: String = "",
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        backgroundColor = backgroundColor
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = icon,
            contentDescription = description,
            tint = iconTint
        )
    }
}