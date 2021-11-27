package com.toybethsystems.dokto.ui.features.registration.patient.third

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.theme.DoktoSecondary
import com.toybethsystems.dokto.ui.common.components.DoktoButton
import com.toybethsystems.dokto.ui.common.components.DoktoDropDownMenu
import com.toybethsystems.dokto.ui.common.components.DoktoTextFiled
import com.toybethsystems.dokto.ui.features.registration.patient.PatientRegistrationViewModel

@Composable
fun PatientRegistrationThirdScreen(
    viewModel: PatientRegistrationViewModel
) {

    val scrollState = rememberScrollState()

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.label_referring_physician),
                modifier = Modifier,
                color = DoktoSecondary,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ------------------------ REFERRING DOCTOR ADDRESS -------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.referringDoctorAddress.state.value ?: "",
            labelResourceId = R.string.label_referring_doctor_address,
            hintResourceId = R.string.hint_referring_doctor_address,
            errorMessage = viewModel.referringDoctorAddress.error.value,
            onValueChange = {
                viewModel.referringDoctorAddress.state.value = it
                viewModel.referringDoctorAddress.error.value = null
            },
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ REFERRING DOCTOR FULL NAME -------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.referringDoctorName.state.value ?: "",
            labelResourceId = R.string.label_referring_doctor_full_name,
            hintResourceId = R.string.hint_referring_doctor_full_name,
            errorMessage = viewModel.referringDoctorName.error.value,
            onValueChange = {
                viewModel.referringDoctorName.state.value = it
                viewModel.referringDoctorName.error.value = null
            },
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ REFERRING DOCTOR PHONE NUMBER -------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.referringDoctorPhoneNumber.state.value ?: "",
            labelResourceId = R.string.label_referring_doctor_phone_number,
            hintResourceId = R.string.hint_referring_doctor_phone_number,
            errorMessage = viewModel.referringDoctorPhoneNumber.error.value,
            onValueChange = {
                viewModel.referringDoctorPhoneNumber.state.value = it
                viewModel.referringDoctorPhoneNumber.error.value = null
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.label_insurance_details),
                modifier = Modifier,
                color = DoktoSecondary,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ------------------------ INSURANCE TYPE -------------------- //

        DoktoDropDownMenu(
            suggestions = viewModel.insuranceTypes,
            textFieldValue = viewModel.getInsuranceType().state.value ?: "",
            labelResourceId = R.string.identification_type,
            hintResourceId = R.string.select,
            errorMessage = viewModel.getInsuranceType().error.value,
            onValueChange = {
                viewModel.setInsuranceType(it)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------- INSURANCE DETAILS SECTION ------------------- //
        AnimatedVisibility(visible = viewModel.showInsuranceDetailsForm.value) {
            Row {
                Column {
                    // ------------------------ INSURANCE NAME -------------------- //

                    DoktoTextFiled(
                        textFieldValue = viewModel.insuranceName.state.value ?: "",
                        labelResourceId = R.string.label_insurance_name,
                        hintResourceId = R.string.hint_insurance_name,
                        errorMessage = viewModel.insuranceName.error.value,
                        onValueChange = {
                            viewModel.insuranceName.state.value = it
                            viewModel.insuranceName.error.value = null
                        },
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    // ------------------------ INSURANCE NAME -------------------- //

                    DoktoTextFiled(
                        textFieldValue = viewModel.insuranceNumber.state.value ?: "",
                        labelResourceId = R.string.label_insurance_number,
                        hintResourceId = R.string.hint_insurance_number,
                        errorMessage = viewModel.insuranceNumber.error.value,
                        onValueChange = {
                            viewModel.insuranceNumber.state.value = it
                            viewModel.insuranceNumber.error.value = null
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    // ------------------------ INSURANCE NAME -------------------- //

                    DoktoTextFiled(
                        textFieldValue = viewModel.insurancePolicyHolderName.state.value ?: "",
                        labelResourceId = R.string.label_insurance_policy_holder_name,
                        hintResourceId = R.string.hint_insurance_policy_holder_name,
                        errorMessage = viewModel.insurancePolicyHolderName.error.value,
                        onValueChange = {
                            viewModel.insurancePolicyHolderName.state.value = it
                            viewModel.insurancePolicyHolderName.error.value = null
                        },
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // -------------------------- NEXT BUTTON -------------------- //

        DoktoButton(textResourceId = R.string.next) {
            viewModel.moveNext()
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}