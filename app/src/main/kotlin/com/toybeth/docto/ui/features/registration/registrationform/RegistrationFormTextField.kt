package com.toybeth.docto.ui.features.registration.registrationform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldPlaceholder

@Composable
fun RegistrationFormTextField(
    textFieldValue: MutableState<String>,
    labelResourceId: Int,
    hintResourceId: Int,
    onClick: (() -> Unit)? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var modifier = Modifier.fillMaxWidth()
    if(onClick != null) {
        modifier = modifier.clickable {
            onClick.invoke()
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = labelResourceId),
            color = Color.White
        )
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
            },
            placeholder = {
                Text(stringResource(id = hintResourceId))
            },
            enabled = onClick == null,
            readOnly = onClick != null,
            singleLine = true,
            modifier = modifier,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.White,
                backgroundColor = DoktoRegistrationFormTextFieldBackground,
                cursorColor = DoktoAccent,
                placeholderColor = DoktoRegistrationFormTextFieldPlaceholder,
                disabledPlaceholderColor = DoktoRegistrationFormTextFieldPlaceholder
            ),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
    }
}