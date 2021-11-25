package com.toybeth.docto.ui.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.base.R
import com.toybeth.docto.base.theme.DoktoError
import com.toybeth.docto.base.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.base.theme.DoktoPrimaryVariant

@Composable
fun DoktoRadioGroup(
    radioOptions: List<String> = listOf(),
    textColor: Color = Color.White,
    errorMessage: String? = null,
    onOptionSelected: (value: String) -> Unit
) {
    if (radioOptions.isNotEmpty()) {
        val selectedOption = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            // --------------------------- RADIO BUTTONS ------------------------ //
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
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 16.sp
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
}