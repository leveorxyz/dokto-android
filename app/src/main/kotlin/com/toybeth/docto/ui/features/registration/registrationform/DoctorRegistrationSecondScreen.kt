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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.common.components.DoktoButton
import com.toybeth.docto.ui.common.components.DoktoDropDownMenu
import com.toybeth.docto.ui.common.components.DoktoTextFiled
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.ui.theme.DoktoSecondary

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun DoctorRegistrationSecondScreen(
    viewModel: RegistrationViewModel,
    showCountrySelectionDialog: () -> Unit,
    showStateSelectionDialog: () -> Unit,
    showCitySelectionDialog: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var selectedIdentification by remember { mutableStateOf("") }

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

    val cornerRadius = LocalDensity.current.run { 16.dp.toPx() }

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
            textFieldValue = selectedIdentification,
            labelResourceId = R.string.identification_type,
            hintResourceId = R.string.select,
            onValueChange = {
                selectedIdentification = it
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ IDENTIFICATION NUMBER -------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.identificationNumber.value,
            labelResourceId = R.string.identification_number,
            hintResourceId = R.string.identification_number,
            errorMessage = viewModel.identificationNumberError.value,
            onValueChange = {
                viewModel.identificationNumber.value = it
                viewModel.identificationNumberError.value = null
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

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = DoktoRegistrationFormTextFieldBackground,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = Color.White,
                    style = stroke,
                    cornerRadius = CornerRadius(
                        x = cornerRadius,
                        y = cornerRadius
                    )
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
                                color = DoktoSecondary
                            ),
                        onClick = {
                            identityImageLauncher.launch("image/*")
                        }) {
                        Icon(
                            Icons.Filled.Edit,
                            "change image",
                            tint = Color.White
                        )
                    }
                }
            } else {
                Row {
                    DoktoButton(textResourceId = R.string.choose_image) {
                        identityImageLauncher.launch("image/*")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------- STREET ADDRESS ---------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.address.value,
            labelResourceId = R.string.label_address,
            hintResourceId = R.string.hint_address,
            errorMessage = viewModel.addressError.value,
            onValueChange = {
                viewModel.address.value = it
                viewModel.addressError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // -------------------------- Country ------------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.selectedCountryName.value,
            labelResourceId = R.string.label_country,
            hintResourceId = R.string.select,
            errorMessage = viewModel.addressError.value,
            onValueChange = {
                viewModel.selectedCountryName.value = it
                viewModel.countryNameError.value = null
            },
            onClick = { showCountrySelectionDialog.invoke() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.select),
                    tint = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------------ STATE ----------------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.selectedStateName.value,
            labelResourceId = R.string.label_state,
            hintResourceId = R.string.select,
            errorMessage = viewModel.stateNameError.value,
            onValueChange = {
                viewModel.selectedStateName.value = it
                viewModel.stateNameError.value = null
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
            textFieldValue = viewModel.selectedCityName.value,
            labelResourceId = R.string.label_city,
            hintResourceId = R.string.select,
            errorMessage = viewModel.cityNameError.value,
            onValueChange = {
                viewModel.selectedCityName.value = it
                viewModel.cityNameError.value = null
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
            textFieldValue = viewModel.zipCode.value,
            labelResourceId = R.string.label_zip_code,
            hintResourceId = R.string.hint_zip_code,
            errorMessage = viewModel.zipCodeError.value,
            onValueChange = {
                viewModel.zipCode.value = it
                viewModel.zipCodeError.value = null
            }
        )
        Spacer(modifier = Modifier.height(50.dp))

        // ----------------------------- NEXT BUTTON ------------------------- //

        DoktoButton(textResourceId =  R.string.next) {

        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}