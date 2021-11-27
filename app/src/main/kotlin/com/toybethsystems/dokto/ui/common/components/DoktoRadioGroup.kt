package com.toybethsystems.dokto.ui.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybethsystems.dokto.base.R
import com.toybethsystems.dokto.base.theme.DoktoError
import com.toybethsystems.dokto.base.theme.DoktoRegistrationFormTextFieldBackground
import com.toybethsystems.dokto.base.theme.DoktoPrimaryVariant

@Composable
fun DoktoRadioGroup(
    radioOptions: List<String>,
    labelResourceId: Int? = null,
    textColor: Color = Color.LightGray,
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
            // ---------------- LABEL --------------- //
            labelResourceId?.let {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(id = it),
                        color = Color.White,
                    )
                }
            }
            // --------------------------- RADIO BUTTONS ------------------------ //
            radioOptions.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (item == selectedOption.value),
                        onClick = {
                            selectedOption.value = item
                            onOptionSelected(item)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DoktoPrimaryVariant,
                            unselectedColor = DoktoRegistrationFormTextFieldBackground
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = textColor,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 16.sp
                            )
                        ) { append(item) }
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