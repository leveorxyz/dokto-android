package com.toybeth.docto.ui.features.registration.registrationform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toybeth.docto.base.R
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.ui.theme.DoktoSecondary

@Composable
fun RadioGroup(
    radioOptions: List<String> = listOf(),
    textColor: Color = Color.White,
    onOptionSelected: (value: String) -> Unit
) {
    if (radioOptions.isNotEmpty()) {
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
                            selectedColor = DoktoSecondary,
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