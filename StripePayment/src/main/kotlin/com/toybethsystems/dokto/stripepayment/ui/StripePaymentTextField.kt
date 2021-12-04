package com.toybethsystems.dokto.stripepayment.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun stripePaymentTextField(
    onValueChange: (String) -> Unit,
    placeholderText: String,
    prefilledText: String = ""
) {
    val textFieldValue = remember { mutableStateOf(prefilledText) }
    TextField(
        textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 12.sp,
                color = Color.Gray
            )
        },
        singleLine = true,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.DarkGray),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}