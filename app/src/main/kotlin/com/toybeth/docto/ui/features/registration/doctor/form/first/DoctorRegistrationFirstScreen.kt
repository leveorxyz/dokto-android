package com.toybeth.docto.ui.features.registration.doctor.form.first

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.common.components.DoktoButton
import com.toybeth.docto.ui.common.components.DoktoTextFiled
import com.toybeth.docto.ui.features.registration.doctor.form.RadioGroup
import com.toybeth.docto.ui.features.registration.doctor.form.RegistrationViewModel
import com.toybeth.docto.ui.theme.DoktoSecondary

@ExperimentalMaterialApi
@Composable
fun DoctorRegistrationFirstScreen(
    viewModel: RegistrationViewModel,
    datePicker: () -> Unit,
    showCountrySelectionDialog: () -> Unit
) {

    val scrollState = rememberScrollState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(
                    context.contentResolver, it
                )

            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(
                state = scrollState,
                enabled = true
            )
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ------------------------- PROFILE IMAGE ----------------------- //

            if (bitmap.value != null) {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_type_doctor),
                    contentDescription = "avatar",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ---------------------- CHOOSE IMAGE ---------------------- //

            DoktoButton(textResourceId = R.string.choose_photo) {
                launcher.launch("image/*")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------------- USER ID -------------------------- //
        DoktoTextFiled(
            textFieldValue = viewModel.userId.value,
            hintResourceId = R.string.hint_userid,
            labelResourceId = R.string.label_userid,
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Help,
                        contentDescription = stringResource(id = R.string.check_availability),
                        tint = DoktoSecondary
                    )
                }
            },
            errorMessage = viewModel.usedIdError.value,
            onValueChange = {
                viewModel.userId.value = it
                viewModel.usedIdError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------------- NAME ----------------------- //
        DoktoTextFiled(
            textFieldValue = viewModel.name.value,
            hintResourceId = R.string.hint_name,
            labelResourceId = R.string.label_name,
            errorMessage = viewModel.nameError.value,
            onValueChange = {
                viewModel.name.value = it
                viewModel.nameError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // -------------------- MOBILE NUMBER ------------------ //

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.label_mobile_number),
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DoktoTextFiled(
                modifier = Modifier.width(120.dp),
                textFieldValue = viewModel.getSelectedCountryCode(),
                hintResourceId = R.string.hint_country_code,
                errorMessage = viewModel.countryError.value,
                onClick = { showCountrySelectionDialog.invoke() },
                onValueChange = {
                    viewModel.countryError.value = null
                }
            )

            DoktoTextFiled(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                textFieldValue = viewModel.mobileNumber.value,
                hintResourceId = R.string.hint_mobile_number,
                errorMessage = viewModel.mobileNumberError.value,
                onValueChange = {
                    viewModel.mobileNumber.value = it
                    viewModel.mobileNumberError.value = null
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // -------------------------- EMAIL ------------------------ //

        DoktoTextFiled(textFieldValue = viewModel.email.value,
            hintResourceId = R.string.hint_email,
            labelResourceId = R.string.label_email,
            errorMessage = viewModel.emailError.value,
            onValueChange = {
                viewModel.email.value = it
                viewModel.emailError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ PASSWORD --------------------- //

        DoktoTextFiled(textFieldValue = viewModel.password.value,
            hintResourceId = R.string.hint_password,
            labelResourceId = R.string.label_password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = viewModel.passwordError.value,
            onValueChange = {
                viewModel.password.value = it
                viewModel.passwordError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------- CONFIRM PASSWORD ---------------------- //

        DoktoTextFiled(textFieldValue = viewModel.confirmPassword.value,
            hintResourceId = R.string.hint_confirm_password,
            labelResourceId = R.string.label_confirm_password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = viewModel.confirmPasswordError.value,
            onValueChange = {
                viewModel.confirmPassword.value = it
                viewModel.confirmPasswordError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ---------------------------- GENDER ------------------------- //

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.hint_gender),
                color = Color.White,
            )
        }
        RadioGroup(
            radioOptions = listOf(
                context.getString(R.string.male),
                context.getString(R.string.female),
                context.getString(R.string.prefer_not_to_say),
            ),
        ) {
            viewModel.gender.value = it
        }
        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------------- DATE OF BIRTH --------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.dateOfBirth.value,
            hintResourceId = R.string.hint_date_of_birth,
            labelResourceId = R.string.label_date_of_birth,
            errorMessage = viewModel.dateOfBirthError.value,
            onClick = { datePicker.invoke() },
            onValueChange = {
                viewModel.dateOfBirth.value = it
                viewModel.dateOfBirthError.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ NEXT BUTTON -------------------- //

        DoktoButton(textResourceId = R.string.next) {
            viewModel.moveNext()
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}