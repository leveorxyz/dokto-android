package com.toybethsystems.dokto.ui.features.registration.doctor.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import com.toybethsystems.dokto.base.R
import com.toybethsystems.dokto.base.theme.DoktoRegistrationFormTextFieldBackground
import com.toybethsystems.dokto.base.theme.DoktoPrimaryVariant

@Composable
fun RadioGroup(
    radioOptions: List<String> = listOf(),
    textColor: Color = Color.White,
    onOptionSelected: (value: String) -> Unit
) {
    if (radioOptions.isNotEmpty()) {
        onOptionSelected.invoke(radioOptions[0])
        val selectedOption = remember { mutableStateOf(radioOptions[0]) }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            radioOptions.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (item == selectedOption.value),
                        onClick = { onOptionSelected(item) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DoktoPrimaryVariant,
                            unselectedColor = DoktoRegistrationFormTextFieldBackground
                        )
                    )

                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = textColor,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                        ) { append("  $item  ") }
                    }

                    ClickableText(
                        text = annotatedString,
                        onClick = {
                            selectedOption.value = item
                            onOptionSelected(item)
                        }
                    )
                }
            }
        }
    }
}