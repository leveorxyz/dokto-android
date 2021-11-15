package com.toybethsystems.dokto.ui.features.registration.doctor.form.fourth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.ui.common.components.*
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RegistrationViewModel
import com.toybethsystems.dokto.base.theme.DoktoError
import com.toybethsystems.dokto.base.theme.DoktoPrimaryVariant
import com.toybethsystems.dokto.base.theme.DoktoSecondary
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DoctorRegistrationFourthScreen(
    viewModel: RegistrationViewModel,
    showDatePicker: ((timeInMillis: Long) -> Unit) -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val insurances = context.resources.getStringArray(R.array.insurances).toMutableList()

    return Column(
        modifier = Modifier
            .verticalScroll(
                state = scrollState,
                enabled = true
            )
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        // ------------------ PROFESSIONAL BIO --------------------- //

        DoktoTextFiled(
            modifier = Modifier.height(150.dp),
            textFieldValue = viewModel.professionalBio.state.value ?: "",
            hintResourceId = R.string.hint_professional_bio,
            labelResourceId = R.string.label_professional_bio,
            errorMessage = viewModel.professionalBio.error.value,
            singleLine = false,
            onValueChange = {
                if (it.length <= 200) {
                    viewModel.professionalBio.state.value = it
                    viewModel.professionalBio.error.value = null
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------- EXPERIENCE -------------------------- //

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.label_experience),
                modifier = Modifier,
                color = DoktoSecondary,
                fontSize = 24.sp
            )
            IconButton(onClick = {
                viewModel.addExperience()
            }) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(id = R.string.add),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ------------------------ EXPERIENCE FORM -------------------------- //

        viewModel.experiences.state.value?.reversed()?.forEachIndexed { index, experience ->

            AnimatedVisibility(visible = (viewModel.experiences.state.value?.size ?: 1) > 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(
                            id = R.string.experience_number,
                            (viewModel.experiences.state.value?.size ?: 0) - index
                        ),
                        modifier = Modifier,
                        color = DoktoPrimaryVariant,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = { viewModel.experiences.state.value?.remove(experience) }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.add),
                            tint = DoktoError
                        )
                    }
                }
            }

            // --------------------------- ESTABLISHMENT NAME -------------------------- //

            DoktoTextFiled(
                textFieldValue = experience.establishmentName.state.value ?: "",
                labelResourceId = R.string.label_establishment_name,
                hintResourceId = R.string.hint_establishment_name,
                errorMessage = experience.establishmentName.error.value,
                onValueChange = {
                    experience.establishmentName.error.value = null
                    experience.establishmentName.state.value = it
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // --------------------------- JOB TITLE ----------------------- //

            DoktoTextFiled(
                textFieldValue = experience.jobTitle.state.value ?: "",
                labelResourceId = R.string.label_job_title,
                hintResourceId = R.string.hint_job_title,
                errorMessage = experience.jobTitle.error.value,
                onValueChange = {
                    experience.jobTitle.error.value = null
                    experience.jobTitle.state.value = it
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // ---------------------- START DATE ----------------------- //

            DoktoTextFiled(
                textFieldValue = experience.startDate.state.value ?: "",
                labelResourceId = R.string.label_start_date,
                hintResourceId = R.string.hint_start_date,
                errorMessage = experience.startDate.error.value,
                onValueChange = {
                    experience.startDate.error.value = null
                },
                onClick = {
                    showDatePicker { timeInMillis ->
                        experience.startDate.state.value = viewModel.getExperienceDateFromMillis(timeInMillis)
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Pick Date",
                        tint = Color.Black.copy(alpha = .7f)
                    )
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // ---------------------- END DATE ----------------------- //

            DoktoTextFiled(
                textFieldValue = experience.endDate.state.value ?: "",
                labelResourceId = R.string.label_end_date,
                hintResourceId = R.string.hint_end_date,
                errorMessage = experience.endDate.error.value,
                onValueChange = {
                    experience.endDate.error.value = null
                },
                onClick = {
                    showDatePicker { timeInMillis ->
                        experience.endDate.state.value = viewModel.getExperienceDateFromMillis(timeInMillis)
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Pick Date",
                        tint = Color.Black.copy(alpha = .7f)
                    )
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // ------------------ JOB DESCRIPTION --------------------- //

            DoktoTextFiled(
                modifier = Modifier.height(150.dp),
                textFieldValue = experience.jobDescription.state.value ?: "",
                hintResourceId = R.string.hint_job_description,
                labelResourceId = R.string.label_job_description,
                errorMessage = experience.jobDescription.error.value,
                singleLine = false,
                onValueChange = {
                    if (it.length <= 200) {
                        experience.jobDescription.state.value = it
                        experience.jobDescription.error.value = null
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

        }

        // -------------------------- LICENSE ------------------------- //

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.label_license_upload),
                color = Color.White,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DoktoImageUpload(
            uploadedImage = viewModel.doctorLicense.state.value,
            errorMessage = viewModel.doctorLicenseUri.error.value
        ) { bitmap, uri ->
            viewModel.doctorLicenseUri.error.value = null
            viewModel.doctorLicense.state.value = bitmap
            viewModel.doctorLicenseUri.state.value = uri
        }
        Spacer(modifier = Modifier.height(30.dp))

        // ---------------------- AWARDS --------------------- //

        DoktoTextFiled(
            modifier = Modifier.height(150.dp),
            textFieldValue = viewModel.doctorAwards.state.value ?: "",
            hintResourceId = R.string.hint_doctor_awards,
            labelResourceId = R.string.label_doctor_awards,
            errorMessage = viewModel.doctorAwards.error.value,
            singleLine = false,
            onValueChange = {
                viewModel.doctorAwards.state.value = it
                viewModel.doctorAwards.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------------- INSURANCES ------------------------- //

        DoktoDropDownMenu(
            suggestions = insurances.sorted(),
            textFieldValue = stringResource(id = R.string.select),
            labelResourceId = R.string.label_accepted_insurances,
            hintResourceId = R.string.select,
            errorMessage = viewModel.doctorInsurances.error.value,
            onValueChange = {
                viewModel.addInsurance(it)
                insurances.remove(it)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(viewModel.doctorInsurances.state.value ?: listOf()) { insurance ->
                insurance.let {
                    DoktoChip(text = it) {
                        insurances.add(it)
                        viewModel.doctorInsurances.state.value?.remove(insurance)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // ------------------------ SUBMIT BUTTON -------------------- //
        DoktoButton(textResourceId = R.string.submit) {
            viewModel.moveNext()
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}