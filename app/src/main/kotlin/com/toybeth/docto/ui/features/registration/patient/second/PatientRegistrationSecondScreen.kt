package com.toybeth.docto.ui.features.registration.patient.second

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.common.components.DoktoButton
import com.toybeth.docto.ui.common.components.DoktoDropDownMenu
import com.toybeth.docto.ui.common.components.DoktoImageUpload
import com.toybeth.docto.ui.common.components.DoktoTextFiled
import com.toybeth.docto.ui.features.registration.doctor.form.RegistrationViewModel
import com.toybeth.docto.ui.features.registration.patient.PatientRegistrationViewModel

@Composable
fun PatientRegistrationSecondScreen(
    viewModel: PatientRegistrationViewModel,
    showCountrySelectionDialog: () -> Unit,
    showStateSelectionDialog: () -> Unit,
    showCitySelectionDialog: () -> Unit,
) {
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val identificationOptions = context.resources.getStringArray(R.array.identification)
    val identityValidationBitmap = remember { mutableStateOf<Bitmap?>(null) }

    return Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(
                state = scrollState,
                enabled = true
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ IDENTIFICATION TYPE -------------------- //

        DoktoDropDownMenu(
            suggestions = identificationOptions.toList(),
            textFieldValue = viewModel.identityVerificationType.state.value ?: "",
            labelResourceId = R.string.identification_type,
            hintResourceId = R.string.select,
            errorMessage = viewModel.identityVerificationType.error.value,
            onValueChange = {
                viewModel.identityVerificationType.state.value = it
                viewModel.identityVerificationType.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ IDENTIFICATION NUMBER -------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.identityVerificationNumber.state.value ?: "",
            labelResourceId = R.string.identification_number,
            hintResourceId = R.string.identification_number,
            errorMessage = viewModel.identityVerificationNumber.error.value,
            onValueChange = {
                viewModel.identityVerificationNumber.state.value = it
                viewModel.identityVerificationNumber.error.value = null
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ---------------------------- UPLOAD IDENTITY --------------------------- //

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.label_upload_identity),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DoktoImageUpload(identityValidationBitmap.value) { bitmap, uri ->
            identityValidationBitmap.value = bitmap
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ SOCIAL SECURITY NUMBER -------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.socialSecurityNumber.state.value ?: "",
            labelResourceId = R.string.label_identification_number,
            hintResourceId = R.string.hint_identification_number,
            errorMessage = viewModel.socialSecurityNumber.error.value,
            onValueChange = {
                viewModel.socialSecurityNumber.state.value = it
                viewModel.socialSecurityNumber.error.value = null
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------- STREET ADDRESS ---------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.address.state.value ?: "",
            labelResourceId = R.string.label_address,
            hintResourceId = R.string.hint_address,
            errorMessage = viewModel.address.error.value,
            onValueChange = {
                viewModel.address.state.value  = it
                viewModel.address.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------------ STATE ----------------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.stateName.state.value ?: "",
            labelResourceId = R.string.label_state,
            hintResourceId = R.string.select,
            errorMessage = viewModel.stateName.error.value,
            onValueChange = {
                viewModel.stateName.state.value = it
                viewModel.stateName.error.value = null
            },
            onClick = { showStateSelectionDialog.invoke() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.select),
                    tint = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------------ CITY ----------------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.cityName.state.value ?: "",
            labelResourceId = R.string.label_city,
            hintResourceId = R.string.select,
            errorMessage = viewModel.cityName.error.value,
            onValueChange = {
                viewModel.cityName.state.value = it
                viewModel.cityName.error.value = null
            },
            onClick = { showCitySelectionDialog.invoke() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.select),
                    tint = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------- ZIP CODE ---------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.zipCode.state.value ?: "",
            labelResourceId = R.string.label_zip_code,
            hintResourceId = R.string.hint_zip_code,
            errorMessage = viewModel.zipCode.error.value,
            onValueChange = {
                viewModel.zipCode.state.value = it
                viewModel.zipCode.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(50.dp))

        // ----------------------------- NEXT BUTTON ------------------------- //

        DoktoButton(textResourceId =  R.string.next) {
            viewModel.moveNext()
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}