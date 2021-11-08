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
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.R
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoSecondary

@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun DoctorRegistrationFirstScreen(
    viewModel: RegistrationViewModel,
    datePicker: () -> Unit,
    showCountrySelectionDialog: () -> Unit
) {

    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        imageUri?.let {
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
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (bitmap.value != null) {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)                       // clip to the circle shape
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
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    launcher.launch("image/*")
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .height(36.dp)
                    .width(140.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DoktoSecondary
                )
            ) {
                Text(text = "Choose", color = Color.White)
            }
//            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = R.string.label_userid),
            color = Color.White
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {

            OutlinedTextField(
                value = viewModel.userId.value,
                onValueChange = {
                    viewModel.userId.value = it
                },
                placeholder = {
                    Text(stringResource(id = R.string.hint_userid))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    backgroundColor = Color.White,
                    cursorColor = DoktoAccent,
                    placeholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                shape = RoundedCornerShape(16.dp)
            )
            Button(
                onClick = {

                },
                modifier = Modifier
//                    .height(IntrinsicSize.Max)
                    .width(120.dp)
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DoktoSecondary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.check_availability),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
        }
        RegistrationFormTextField(
            viewModel.name,
            R.string.label_name,
            R.string.hint_name
        )
        Spacer(modifier = Modifier.height(30.dp))
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
            OutlinedTextField(
                modifier = Modifier
                    .width(120.dp)
                    .clickable {
                        showCountrySelectionDialog.invoke()
                    },
                readOnly = true,
                enabled = false,
                placeholder = {
                    Text(stringResource(id = R.string.hint_country_code))
                },
                value = viewModel.getSelectedCountryCode(),
                onValueChange = { },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    backgroundColor = Color.White,
                    cursorColor = DoktoAccent,
                    placeholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                shape = RoundedCornerShape(16.dp)
            )
            TextField(
                value = viewModel.mobileNumber.value,
                onValueChange = {
                    viewModel.mobileNumber.value = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                placeholder = { Text(stringResource(id = R.string.hint_mobile_number)) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    backgroundColor = Color.White,
                    cursorColor = DoktoAccent,
                    placeholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }
        RegistrationFormTextField(
            viewModel.email,
            R.string.label_email,
            R.string.hint_email
        )
        RegistrationFormTextField(
            viewModel.password,
            R.string.label_password,
            R.string.hint_password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        RegistrationFormTextField(
            viewModel.confirmPassword,
            R.string.label_confirm_password,
            R.string.hint_confirm_password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(30.dp))
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
        RegistrationFormTextField(
            viewModel.dateOfBirth,
            R.string.label_date_of_birth,
            R.string.hint_date_of_birth,
            onClick = {
                datePicker.invoke()
            },
            keyboardOptions = KeyboardOptions.Default
        )
        Spacer(modifier = Modifier.height(50.dp))
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
                backgroundColor = DoktoSecondary
            )
        ) {
            Text(text = stringResource(id = R.string.next), color = Color.White)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}