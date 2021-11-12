package com.toybeth.docto.ui.features.forgetpassword.enterotp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orhanobut.logger.Logger
import com.toybeth.docto.ui.theme.DoktoPrimaryVariant
import com.toybeth.docto.ui.theme.TextColorWhite
import kotlin.math.max

private val firstTextFieldFocusRequester = FocusRequester()
private val secondTextFieldFocusRequester = FocusRequester()
private val thirdTextFieldFocusRequester = FocusRequester()
private val fourthTextFieldFocusRequester = FocusRequester()

private val emptyString = String(intArrayOf(1), 0, 1)

@Composable
fun OtpField(
    onValueChangeCallback: (value: String) -> Unit
) {

    val firstCharacterOfOtp = remember { mutableStateOf(StringBuilder("")) }
    val secondCharacterOfOtp = remember { mutableStateOf(StringBuilder(emptyString)) }
    val thirdCharacterOfOtp = remember { mutableStateOf(StringBuilder(emptyString)) }
    val fourthCharacterOfOtp = remember { mutableStateOf(StringBuilder(emptyString)) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.width(20.dp))

        TextField(
            value = TextFieldValue(
                text = firstCharacterOfOtp.value.toString(),
                selection = TextRange(
                    max(0, firstCharacterOfOtp.value.length),
                    firstCharacterOfOtp.value.length
                )
            ),
            onValueChange = {
                firstCharacterOfOtp.value = StringBuilder(it.text)
                onValueChange(
                    firstCharacterOfOtp.value,
                    secondCharacterOfOtp.value,
                    thirdCharacterOfOtp.value,
                    fourthCharacterOfOtp.value,
                    onValueChangeCallback
                )
            },
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .weight(1f)
                .background(DoktoPrimaryVariant)
                .focusRequester(firstTextFieldFocusRequester),
            textStyle = LocalTextStyle.current.copy(
                color = TextColorWhite,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            shape = RoundedCornerShape(10f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.width(20.dp))

        TextField(
            value = TextFieldValue(
                text = secondCharacterOfOtp.value.toString(),
                selection = TextRange(
                    max(0, secondCharacterOfOtp.value.length),
                    secondCharacterOfOtp.value.length
                )
            ),
            onValueChange = {
                secondCharacterOfOtp.value = StringBuilder(it.text)
                onValueChange(
                    firstCharacterOfOtp.value,
                    secondCharacterOfOtp.value,
                    thirdCharacterOfOtp.value,
                    fourthCharacterOfOtp.value,
                    onValueChangeCallback
                )
            },
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .weight(1f)
                .background(DoktoPrimaryVariant)
                .focusRequester(secondTextFieldFocusRequester),
            textStyle = LocalTextStyle.current.copy(
                color = TextColorWhite,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            shape = RoundedCornerShape(10f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation =
            if (secondCharacterOfOtp.value.firstOrNull()?.code in 1..2)
                VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.width(20.dp))

        TextField(
            value = TextFieldValue(
                text = thirdCharacterOfOtp.value.toString(),
                selection = TextRange(
                    max(0, thirdCharacterOfOtp.value.length),
                    thirdCharacterOfOtp.value.length
                )
            ),
            onValueChange = {
                thirdCharacterOfOtp.value = StringBuilder(it.text)
                onValueChange(
                    firstCharacterOfOtp.value,
                    secondCharacterOfOtp.value,
                    thirdCharacterOfOtp.value,
                    fourthCharacterOfOtp.value,
                    onValueChangeCallback
                )
            },
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .weight(1f)
                .background(DoktoPrimaryVariant)
                .focusRequester(thirdTextFieldFocusRequester),
            textStyle = LocalTextStyle.current.copy(
                color = TextColorWhite,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            shape = RoundedCornerShape(10f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation =
            if (thirdCharacterOfOtp.value.firstOrNull()?.code in 1..2)
                VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.width(20.dp))

        TextField(
            value = TextFieldValue(
                text = fourthCharacterOfOtp.value.toString(),
                selection = TextRange(
                    max(0, fourthCharacterOfOtp.value.length),
                    fourthCharacterOfOtp.value.length
                )
            ),
            onValueChange = {
                fourthCharacterOfOtp.value = StringBuilder(it.text)
                onValueChange(
                    firstCharacterOfOtp.value,
                    secondCharacterOfOtp.value,
                    thirdCharacterOfOtp.value,
                    fourthCharacterOfOtp.value,
                    onValueChangeCallback
                )
            },
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .weight(1f)
                .background(DoktoPrimaryVariant)
                .focusRequester(fourthTextFieldFocusRequester),
            textStyle = LocalTextStyle.current.copy(
                color = TextColorWhite,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            shape = RoundedCornerShape(10f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation =
            if (fourthCharacterOfOtp.value.firstOrNull()?.code in 1..2)
                VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Composable
private fun OtpTextField(
    valueState: MutableState<StringBuilder>,
    focusRequester: FocusRequester,
    firstCharacterOfOtp: MutableState<StringBuilder>,
    secondCharacterOfOtp: MutableState<StringBuilder>,
    thirdCharacterOfOtp: MutableState<StringBuilder>,
    fourthCharacterOfOtp: MutableState<StringBuilder>,
    onValueChange: (value: String) -> Unit
) {
    TextField(
        value = TextFieldValue(
            text = valueState.value.toString(),
            selection = TextRange(
                max(0, valueState.value.length),
                valueState.value.length
            )
        ),
        onValueChange = {
            valueState.value = StringBuilder(it.text)
            onValueChange(
                firstCharacterOfOtp.value,
                secondCharacterOfOtp.value,
                thirdCharacterOfOtp.value,
                fourthCharacterOfOtp.value,
                callback = onValueChange
            )
        },
        modifier = Modifier
            .height(60.dp)
            .width(60.dp)
            .background(DoktoPrimaryVariant)
            .focusRequester(focusRequester),
        textStyle = LocalTextStyle.current.copy(
            color = TextColorWhite,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        shape = RoundedCornerShape(10f),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
        ),
        visualTransformation = if (valueState.value.firstOrNull()?.code in 1..2)
            VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

private fun onValueChange(
    firstChar: StringBuilder,
    secondChar: StringBuilder,
    thirdChar: StringBuilder,
    fourthChar: StringBuilder,
    callback: (value: String) -> Unit
) {
    var fullText = ""
    try {
        if (firstChar.length > 1) {
            secondChar.replace(0, secondChar.length, firstChar.substring(1, firstChar.length))
            firstChar.delete(1, firstChar.length)
        }
        val mFirstChar = firstChar.toString().toInt()
        fullText += mFirstChar

        try {
            if (secondChar.length >= 2 && secondChar.first().code in 1..2) {
                secondChar.delete(0, secondChar.length - 1)
            } else if (secondChar.length > 1) {
                thirdChar.replace(0, thirdChar.length, secondChar.substring(1, secondChar.length))
                secondChar.delete(1, secondChar.length)
            }
            val mSecondChar = secondChar.toString().toInt()
            fullText += mSecondChar

            try {
                if (thirdChar.length >= 2 && thirdChar.first().code in 1..2) {
                    thirdChar.delete(0, thirdChar.length - 1)
                } else if (thirdChar.length > 1) {
                    fourthChar.replace(
                        0,
                        fourthChar.length,
                        thirdChar.substring(1, thirdChar.length)
                    )
                    thirdChar.delete(1, thirdChar.length)
                }
                val mThirdChar = thirdChar.toString().toInt()
                fullText += mThirdChar

                try {
                    if (fourthChar.length >= 2 && fourthChar.first().code in 1..2) {
                        fourthChar.delete(0, fourthChar.length - 1)
                    } else if (fourthChar.length > 1) {
                        fourthChar.delete(1, fourthChar.length)
                    }
                    val mFourthChar = fourthChar.toString().toInt()
                    fullText += mFourthChar

                } catch (e: Exception) {
                    if (fourthChar.isNotEmpty() && fourthChar[0].code == 1) {
                        Logger.d("here")
                        fullText += fourthChar[0].toString()
                    } else {
                        Logger.d("here")
                        fourthChar.append(String(intArrayOf(2), 0, 1))
                    }
                    e.printStackTrace()
                }

            } catch (e: Exception) {
                if (thirdChar.isEmpty() && fourthChar.isNotEmpty() && fourthChar.first().code == 2) {
                    fourthChar.replace(0, 1, emptyString)
                }
                if (thirdChar.isNotEmpty() && thirdChar[0].code == 1) {
                    Logger.d("here")
                    fullText += thirdChar[0].toString()
                } else {
                    Logger.d("here")
                    thirdChar.append(String(intArrayOf(2), 0, 1))
                }
                e.printStackTrace()
            }

        } catch (e: Exception) {
            if (secondChar.isEmpty() && thirdChar.isNotEmpty() && thirdChar.first().code == 2) {
                thirdChar.replace(0, 1, emptyString)
            }
            if (secondChar.isNotEmpty() && secondChar[0].code == 1) {
                Logger.d("here")
                fullText += secondChar[0].toString()
            } else {
                Logger.d("here")
                secondChar.append(String(intArrayOf(2), 0, 1))
            }
            e.printStackTrace()
        }

    } catch (e: Exception) {
        if (firstChar.isEmpty() && secondChar.isNotEmpty() && secondChar.first().code == 2) {
            secondChar.replace(0, 1, emptyString)
        }
        e.printStackTrace()
    }
    callback.invoke(fullText)
    refreshFocusRequester(
        fullText,
        firstTextFieldFocusRequester,
        secondTextFieldFocusRequester,
        thirdTextFieldFocusRequester,
        fourthTextFieldFocusRequester
    )
}

internal fun refreshFocusRequester(
    value: String,
    firstTextFieldFocusRequester: FocusRequester,
    secondTextFieldFocusRequester: FocusRequester,
    thirdTextFieldFocusRequester: FocusRequester,
    fourthTextFieldFocusRequester: FocusRequester
) {
    Logger.d("$value length = ${value.length}")
    when {
        value.length < 2 -> {
            firstTextFieldFocusRequester.requestFocus()
            secondTextFieldFocusRequester.freeFocus()
            thirdTextFieldFocusRequester.freeFocus()
            fourthTextFieldFocusRequester.freeFocus()
        }
        value.length < 3 -> {
            firstTextFieldFocusRequester.freeFocus()
            secondTextFieldFocusRequester.requestFocus()
            thirdTextFieldFocusRequester.freeFocus()
            fourthTextFieldFocusRequester.freeFocus()
        }
        value.length < 4 -> {
            firstTextFieldFocusRequester.freeFocus()
            secondTextFieldFocusRequester.freeFocus()
            thirdTextFieldFocusRequester.requestFocus()
            fourthTextFieldFocusRequester.freeFocus()
        }
        value.length < 5 -> {
            firstTextFieldFocusRequester.freeFocus()
            secondTextFieldFocusRequester.freeFocus()
            thirdTextFieldFocusRequester.freeFocus()
            fourthTextFieldFocusRequester.requestFocus()
        }
        else -> {
            firstTextFieldFocusRequester.freeFocus()
            secondTextFieldFocusRequester.freeFocus()
            thirdTextFieldFocusRequester.freeFocus()
            fourthTextFieldFocusRequester.freeFocus()
        }
    }
}
