package com.toybeth.docto.ui.common.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toybeth.docto.ui.theme.DoktoSecondary

@Composable
fun DoktoButton(
    modifier: Modifier = Modifier,
    textResourceId: Int,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(48.dp)
            .width(200.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DoktoSecondary
        )
    ) {
        Text(text = stringResource(id = textResourceId), color = Color.White)
    }
}