package com.toybeth.docto.ui.features.registration.registrationform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackgroun

@Composable
fun RegistrationFormTextField(
    textFieldValue: MutableState<String>,
    hintResourceId: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = hintResourceId),
            color = Color.White
        )
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = DoktoRegistrationFormTextFieldBackgroun,
                cursorColor = DoktoAccent
            )
        )
    }
}