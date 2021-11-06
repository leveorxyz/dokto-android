package com.toybeth.docto.ui.features.registration.registrationform

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.features.login.components.DoktoTextField
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackgroun
import com.toybeth.docto.ui.theme.DoktoSecondary

@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun RegistrationFirstStepFormScreen() {
    return Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scrollState = rememberScrollState()
        val userId = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }
        val mobileNumber = remember { mutableStateOf("") }
        val countryCode = remember { mutableStateOf("") }
        val expanded = remember{ mutableStateOf(false) }

        Column(
            modifier = Modifier
                .verticalScroll(
                    state = scrollState,
                    enabled = true
                )
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            RegistrationFormTextField(
                userId,
                R.string.hint_userid
            )
            RegistrationFormTextField(
                name,
                R.string.hint_name
            )
            RegistrationFormTextField(
                mobileNumber,
                R.string.hint_mobile_number
            )
            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = {
                    expanded.value = !it
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    readOnly = true,
                    value = countryCode.value,
                    onValueChange = { },
                    label = { Text("Label") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded.value
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = {
                        expanded.value = false
                    }
                ) {
                    listOf<String>("Option 1", "Option 2").forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                countryCode.value = selectionOption
                                expanded.value = false
                            }
                        ) {
                            Text(text = selectionOption)
                        }
                    }
                }
            }
        }
    }
}