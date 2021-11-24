package com.toybeth.docto.ui.features.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.base.theme.DoktoError

@Composable
fun DoktoTextField(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    error: String,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            value = value,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = label,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White,
                cursorColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = Color.White
                )
            },
            singleLine = maxLines == 1,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = Color.White
            ),
            keyboardOptions = keyboardOptions,
            isError = error.isNotEmpty()
        )
        AnimatedVisibility(visible = error.isNotEmpty()) {
            Text(
                error,
                modifier = Modifier.padding(start = 15.dp),
                color = DoktoError,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun DoktoTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    error: String,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            value = value,
            onValueChange = { onValueChange(it) },
            label = {
                Text(
                    text = label,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.White
                )
            },
            singleLine = maxLines == 1,
            textStyle = TextStyle(
                color = Color.White
            ),
            maxLines = maxLines,
            isError = error.isNotEmpty()
        )
        AnimatedVisibility(visible = error.isNotEmpty()) {
            Text(
                error,
                modifier = Modifier.padding(start = 15.dp),
                color = DoktoError,
                fontSize = 12.sp
            )
        }
    }
}