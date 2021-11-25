package com.toybeth.docto.ui.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.base.theme.DoktoError
import com.toybeth.docto.base.theme.DoktoSecondary

@Composable
fun DoktoTextFiled(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    hintResourceId: Int,
    labelResourceId: Int? = null,
    isPasswordField: Boolean = false,
    onValueChange: (String) -> Unit,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null
) {
    var textFieldModifier = if (singleLine) Modifier.fillMaxWidth() else Modifier.fillMaxSize()
    var passwordVisibility by remember { mutableStateOf(false) }
    val visibilityIcon =
        if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    if (onClick != null) {
        textFieldModifier = textFieldModifier.clickable {
            onClick.invoke()
        }
    }
    Column(
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {
        // -------------------- LABEL ----------------------- //
        labelResourceId?.let {
            Text(
                stringResource(id = labelResourceId),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ------------------- TEXT FIELD -------------------- //
        OutlinedTextField(
            value = if (keyboardOptions.keyboardType == KeyboardType.Number) {
                textFieldValue.filter { it.isDigit() }
            } else {
                textFieldValue
            },
            onValueChange = { onValueChange(it) },
            placeholder = { Text(stringResource(id = hintResourceId)) },
            enabled = onClick == null,
            readOnly = onClick != null,
            singleLine = singleLine,
            modifier = textFieldModifier,
            isError = errorMessage != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Black,
                backgroundColor = Color.White,
                cursorColor = DoktoSecondary,
                errorBorderColor = DoktoError,
                placeholderColor = Color.Gray,
                disabledPlaceholderColor = Color.Gray,
                unfocusedBorderColor = if (errorMessage == null) Color.White else DoktoError,
                focusedBorderColor = if (errorMessage == null) DoktoSecondary else DoktoError
            ),
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isPasswordField && !passwordVisibility) {
                PasswordVisualTransformation()
            } else {
                visualTransformation
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = leadingIcon,
            trailingIcon = if (isPasswordField) {
                {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            imageVector = visibilityIcon,
                            contentDescription = "",
                            tint = Color.DarkGray
                        )
                    }
                }
            } else {
                trailingIcon
            },
        )

        // ---------------------- ERROR MESSAGE ------------------- //
        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = DoktoError,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 15.dp, top = 3.dp)
                )
            }
        }
    }
}