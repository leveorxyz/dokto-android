package com.toybethsystems.dokto.ui.features.registration.clinic.first

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.theme.DoktoSecondary
import com.toybethsystems.dokto.ui.common.components.DoktoButton
import com.toybethsystems.dokto.ui.common.components.DoktoTextFiled
import com.toybethsystems.dokto.ui.features.registration.clinic.ClinicRegistrationViewModel
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RadioGroup

@Composable
fun ClinicRegistrationFirstScreen(
    viewModel: ClinicRegistrationViewModel,
    showCountrySelectionDialog: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.profileImageUri.state.value = uri
        viewModel.profileImageUri.state.value ?.let {
            if (Build.VERSION.SDK_INT < 28) {
                viewModel.profileImage.state.value = MediaStore.Images.Media.getBitmap(
                    context.contentResolver, it
                )

            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                viewModel.profileImage.state.value = ImageDecoder.decodeBitmap(source)
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

            if (viewModel.profileImage.state.value != null) {
                Image(
                    bitmap = viewModel.profileImage.state.value!!.asImageBitmap(),
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
            modifier = Modifier.onFocusChanged {
                if(!it.hasFocus) {
                    viewModel.checkIfUserNameAvailable()
                }
            },
            textFieldValue = viewModel.userId.state.value ?: "",
            hintResourceId = R.string.hint_userid,
            labelResourceId = R.string.label_userid,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.checkIfUserNameAvailable()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ManageSearch,
                        contentDescription = stringResource(id = R.string.check_availability),
                        tint = DoktoSecondary
                    )
                }
            },
            errorMessage = viewModel.userId.error.value,
            onValueChange = {
                viewModel.userId.state.value = it
                viewModel.userId.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------------- NAME ----------------------- //
        DoktoTextFiled(
            textFieldValue = viewModel.name.state.value ?: "",
            hintResourceId = R.string.hint_clinic_name,
            labelResourceId = R.string.label_clinic_name,
            errorMessage = viewModel.name.error.value,
            onValueChange = {
                viewModel.name.state.value = it
                viewModel.name.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ---------------------- ADRESS --------------------- //

        DoktoTextFiled(
            modifier = Modifier.height(150.dp),
            textFieldValue = viewModel.address.state.value ?: "",
            hintResourceId = R.string.hint_address,
            labelResourceId = R.string.label_address,
            errorMessage = viewModel.address.error.value,
            singleLine = false,
            onValueChange = {
                viewModel.address.state.value = it
                viewModel.address.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ PASSWORD --------------------- //

        DoktoTextFiled(textFieldValue = viewModel.password.state.value ?: "",
            hintResourceId = R.string.hint_password,
            labelResourceId = R.string.label_password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = viewModel.password.error.value,
            onValueChange = {
                viewModel.password.state.value = it
                viewModel.password.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------- CONFIRM PASSWORD ---------------------- //

        DoktoTextFiled(textFieldValue = viewModel.confirmPassword.state.value ?: "",
            hintResourceId = R.string.hint_confirm_password,
            labelResourceId = R.string.label_confirm_password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = viewModel.confirmPassword.error.value,
            onValueChange = {
                viewModel.confirmPassword.state.value = it
                viewModel.confirmPassword.error.value = null
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
                errorMessage = viewModel.country.error.value,
                onClick = { showCountrySelectionDialog.invoke() },
                onValueChange = {
                    viewModel.country.error.value = null
                }
            )

            DoktoTextFiled(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                textFieldValue = viewModel.mobileNumber.state.value ?: "",
                hintResourceId = R.string.hint_mobile_number,
                errorMessage = viewModel.mobileNumber.error.value,
                onValueChange = {
                    viewModel.mobileNumber.state.value = it
                    viewModel.mobileNumber.error.value = null
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // -------------------------- EMAIL ------------------------ //

        DoktoTextFiled(textFieldValue = viewModel.email.state.value ?: "",
            hintResourceId = R.string.hint_email,
            labelResourceId = R.string.label_email,
            errorMessage = viewModel.email.error.value,
            onValueChange = {
                viewModel.email.state.value = it
                viewModel.email.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ---------------------------- NUMBER OF PRACTITIONERS ------------------------- //

        DoktoTextFiled(textFieldValue = viewModel.numberOfPractitioner.state.value ?: "",
            hintResourceId = R.string.hint_number_of_practitioner,
            labelResourceId = R.string.label_number_of_practitioner,
            errorMessage = viewModel.numberOfPractitioner.error.value,
            onValueChange = {
                viewModel.numberOfPractitioner.state.value = it
                viewModel.numberOfPractitioner.error.value = null
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