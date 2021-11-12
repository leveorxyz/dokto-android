package com.toybeth.docto.ui.features.registration.registrationform

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.theme.*

@ExperimentalUnitApi
@Composable
fun DoctorRegistrationThirdScreen(
    viewModel: RegistrationViewModel,
    showDatePicker: ((timeInMillis: Long) -> Unit) -> Unit,
    showSpecialityPicker: () -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val availableLanguages = context.resources.getStringArray(R.array.languages).toList()

    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
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
                        .clickable {
                            if(viewModel.selectedLanguages.contains(language)) {
                                viewModel.selectedLanguages.remove(language)
                            } else {
                                viewModel.selectedLanguages.add(language)
                            }
                        }
                ) {
                    Checkbox(
                        checked = viewModel.selectedLanguages.contains(language),
                        onCheckedChange = {
                            if(it) {
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
                        fontSize = TextUnit(value = 14f, type = TextUnitType.Sp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = R.string.label_education),
                modifier = Modifier.weight(1f),
                color = DoktoPrimaryVariant,
                fontSize = TextUnit(value = 24f, type = TextUnitType.Sp)
            )
            IconButton(onClick = {
                viewModel.addEducation()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        viewModel.educations.forEach { education ->
            val bitmap = remember {
                mutableStateOf<Bitmap?>(null)
            }

            val certificateImageLauncher = rememberLauncherForActivityResult(
                contract =
                ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                education.certificateUri = uri
                education.certificateUri ?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }
                }
            }
            RegistrationFormTextField(
                education.college,
                R.string.label_college,
                R.string.hint_college
            )
            RegistrationFormTextField(
                education.courseStudied,
                R.string.label_course_studied,
                R.string.hint_course_studied
            )
            RegistrationFormTextField(
                education.graduationYear,
                R.string.label_year_graduated,
                R.string.hint_year_graduated
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(id = R.string.label_upload_certification),
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        color = DoktoRegistrationFormTextFieldBackground
                    )
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRoundRect(
                        color = Color.White,
                        style = stroke
                    )
                }
                if (bitmap.value != null) {
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = "certificate",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            modifier = Modifier
                                .then(Modifier.size(24.dp))
                                .clip(CircleShape)
                                .background(
                                    color = DoktoRegistrationFormTextFieldPlaceholder
                                ),
                            onClick = { }) {
                            Icon(
                                Icons.Filled.Edit,
                                "change image",
                                tint = Color.White
                            )
                        }
                    }
                } else {
                    Row {
                        Button(
                            onClick = {
                                certificateImageLauncher.launch("image/*")
                            },
                            modifier = Modifier
                                .height(IntrinsicSize.Max)
                                .width(150.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DoktoPrimaryVariant
                            )
                        ) {
                            Text(
                                text = "Choose Image...",
                                color = Color.White,
                                fontSize = TextUnit(value = 12f, type = TextUnitType.Sp)
                            )
                        }
                    }
                }
            }
            RegistrationFormTextField(
                textFieldValue = education.courseStudied,
                labelResourceId = R.string.label_specialities,
                hintResourceId = R.string.hint_specialities,
                onClick = {
                    showSpecialityPicker.invoke()
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
        Button(
            onClick = {
                viewModel.moveNext()
            },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .height(56.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DoktoPrimaryVariant
            )
        ) {
            Text(text = stringResource(id = R.string.next), color = Color.White)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}