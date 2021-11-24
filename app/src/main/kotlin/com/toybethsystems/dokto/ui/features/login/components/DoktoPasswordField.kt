package com.toybethsystems.dokto.ui.features.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybethsystems.dokto.base.theme.DoktoError

@Composable
fun DoktoPasswordField(
    value: String,
    label: String,
    icon: ImageVector,
    error: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
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
            placeholder = {
                Text(
                    text = "",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = Color.White
            ),
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val visibilityIcon = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
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