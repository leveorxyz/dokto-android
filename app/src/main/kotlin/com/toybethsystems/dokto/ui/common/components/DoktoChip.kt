package com.toybethsystems.dokto.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybethsystems.dokto.base.theme.DoktoRegistrationFormTextFieldBackground

@Composable
fun DoktoChip(
    text: String,
    onDelete: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .background(
                color = DoktoRegistrationFormTextFieldBackground,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        onDelete?.let {
            IconButton(onClick = { it.invoke() }) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}