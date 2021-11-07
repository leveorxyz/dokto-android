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
import com.toybeth.docto.data.City
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldPlaceholder
import com.toybeth.docto.ui.theme.DoktoSecondary

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun DoctorRegistrationSecondScreen(
    viewModel: RegistrationViewModel,
    showStateSelectionDialog: () -> Unit,
    showCitySelectionDialog: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var selectedIdentification by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val zipCode = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val selectedState = viewModel.selectedStateName
    val selectedCity = viewModel.selectedCityName
    val city: MutableState<City?> = remember { mutableStateOf(null) }

    val context = LocalContext.current
    val identificationOptions = context.resources.getStringArray(R.array.identification)
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    var identityImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val identityImageLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        identityImageUri = uri
        identityImageUri?.let {
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
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.label_identification),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = selectedIdentification,
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.hint_identification))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = DoktoRegistrationFormTextFieldBackground,
                        cursorColor = DoktoAccent,
                        placeholderColor = DoktoRegistrationFormTextFieldPlaceholder,
                        disabledPlaceholderColor = DoktoRegistrationFormTextFieldPlaceholder
                    )
                )
                ExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    identificationOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                selectedIdentification = selectionOption
                                expanded = false
                            }
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = selectionOption
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.label_upload_identity),
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
                    contentDescription = "avatar",
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
                            identityImageLauncher.launch("image/*")
                        },
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = DoktoSecondary
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
            textFieldValue = zipCode,
            labelResourceId = R.string.label_zip_code,
            hintResourceId = R.string.hint_zip_code,
        )
        RegistrationFormTextField(
            textFieldValue = address,
            labelResourceId = R.string.label_address,
            hintResourceId = R.string.hint_address,
        )
        RegistrationFormTextField(
            textFieldValue = selectedState,
            labelResourceId = R.string.label_state,
            hintResourceId = R.string.hint_state,
            onClick = {
                showStateSelectionDialog.invoke()
            }
        )
        RegistrationFormTextField(
            textFieldValue = selectedCity,
            labelResourceId = R.string.label_city,
            hintResourceId = R.string.hint_city,
            onClick = {
                showCitySelectionDialog.invoke()
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .height(56.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DoktoSecondary
            )
        ) {
            Text(text = stringResource(id = R.string.next), color = Color.White)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}