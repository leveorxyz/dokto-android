package com.toybeth.docto.ui.features.registration.registrationform.third

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.toybeth.docto.R
import com.toybeth.docto.ui.common.components.*
import com.toybeth.docto.ui.features.registration.registrationform.RegistrationViewModel
import com.toybeth.docto.ui.theme.DoktoCheckboxUncheckColor
import com.toybeth.docto.ui.theme.DoktoError
import com.toybeth.docto.ui.theme.DoktoPrimaryVariant
import com.toybeth.docto.ui.theme.DoktoSecondary
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DoctorRegistrationThirdScreen(
    viewModel: RegistrationViewModel,
    showDatePicker: ((timeInMillis: Long) -> Unit) -> Unit,
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val availableLanguages = context.resources.getStringArray(R.array.languages).toList()
    val specialties = context.resources.getStringArray(R.array.specialities).toMutableList()
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

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

        // -------------------------- Languages ------------------------ //
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.hint_languages),
                color = Color.White,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            availableLanguages.forEach { language ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (viewModel.selectedLanguages.contains(language)) {
                                viewModel.selectedLanguages.remove(language)
                            } else {
                                viewModel.selectedLanguages.add(language)
                            }
                        }
                ) {
                    Checkbox(
                        checked = viewModel.selectedLanguages.contains(language),
                        onCheckedChange = {
                            if (it) {
                                viewModel.selectedLanguages.add(language)
                            } else {
                                viewModel.selectedLanguages.remove(language)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DoktoPrimaryVariant,
                            uncheckedColor = DoktoCheckboxUncheckColor
                        )
                    )
                    Text(
                        language,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))


        // ------------------------- EDUCATIONAL PROFILE -------------------------- //

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.label_education),
                modifier = Modifier,
                color = DoktoSecondary,
                fontSize = 24.sp
            )
            IconButton(onClick = {
                viewModel.addEducation()
            }) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(id = R.string.add),
                    tint = Color.White
                )
            }
        }

        // ------------------------ EDUCATION FORM -------------------------- //

        viewModel.educations.reversed().forEachIndexed { index, education ->

            AnimatedVisibility(visible = viewModel.educations.size > 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(
                            id = R.string.education_profile_number,
                            viewModel.educations.size - index
                        ),
                        modifier = Modifier,
                        color = DoktoPrimaryVariant,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = {
                        viewModel.addEducation()
                    }) {
                        IconButton(onClick = { viewModel.educations.remove(education) }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(id = R.string.add),
                                tint = DoktoError
                            )
                        }
                    }
                }
            }

            // --------------------------------- COLLEGE -------------------------- //

            DoktoTextFiled(
                textFieldValue = education.college.state.value,
                labelResourceId = R.string.label_college,
                hintResourceId = R.string.hint_college,
                errorMessage = education.college.error.value,
                onValueChange = {
                    education.college.error.value = null
                    education.college.state.value = it
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // --------------------------- COURSE STUDIED ----------------------- //

            DoktoTextFiled(
                textFieldValue = education.courseStudied.state.value,
                labelResourceId = R.string.label_course_studied,
                hintResourceId = R.string.hint_course_studied,
                errorMessage = education.courseStudied.error.value,
                onValueChange = {
                    education.courseStudied.error.value = null
                    education.courseStudied.state.value = it
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // ---------------------- GRADUATION YEAR ----------------------- //

            DoktoTextFiled(
                textFieldValue = education.graduationYear.state.value,
                labelResourceId = R.string.label_year_graduated,
                hintResourceId = R.string.hint_year_graduated,
                errorMessage = education.college.error.value,
                onValueChange = {
                    education.college.error.value = null
                },
                onClick = {
                    showDatePicker { timeInMillis ->
                        education.graduationYear.state.value = formatter.format(
                            Date(timeInMillis)
                        )
                    }
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Pick Date",
                        tint = Color.Black
                    )
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // -------------------------- CERTIFICATE ------------------------- //

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(id = R.string.label_upload_certification),
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            DoktoImageUpload(
                uploadedImage = education.certificate.state.value,
                errorMessage = education.certificate.error.value
            ) {
                education.certificate.error.value = null
                education.certificate.state.value = it
            }

            Spacer(modifier = Modifier.height(30.dp))
        }


        // --------------------------- SPECIALITIES ------------------------- //

        DoktoDropDownMenu(
            suggestions = specialties,
            textFieldValue = stringResource(id = R.string.label_specialities),
            labelResourceId = R.string.label_specialities,
            hintResourceId = R.string.hint_specialities,
//            errorMessage = education.speciality.error.value,
            onValueChange = {
//                education.speciality.error.value = null
                viewModel.addSpecialty(it)
                specialties.remove(it)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(viewModel.specialties) { specialty ->
                specialty.value?.let {
                    DoktoChip(text = it) {
                        viewModel.specialties.remove(specialty)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
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