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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldBackground
import com.toybeth.docto.ui.theme.DoktoRegistrationFormTextFieldPlaceholder
import com.toybeth.docto.ui.theme.DoktoSecondary
import com.toybeth.docto.utils.Countries
import java.util.*

@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun RegistrationFirstStepFormScreen(
    datePicker: ((day: Int, month: Int, year: Int) -> Unit) -> Unit
) {
    return Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scrollState = rememberScrollState()
        val userId = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }
        val mobileNumber = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }
        var selectedCountryCode by remember { mutableStateOf(Countries.list.first().countryCode.toString()) }
        var expanded by remember{ mutableStateOf(false) }
        val options = Countries.list
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val context = LocalContext.current
        val bitmap =  remember {
            mutableStateOf<Bitmap?>(null)
        }
        val dateOfBirth = remember { mutableStateOf("") }

        val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver,it)

                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver,it)
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
            Box(
                contentAlignment = Alignment.Center
            ) {
                if(bitmap.value != null) {
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
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(150.dp))
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
                }
            }
            RegistrationFormTextField(
                userId,
                R.string.label_userid,
                R.string.hint_userid
            )
            RegistrationFormTextField(
                name,
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
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ExposedDropdownMenuBox(
                    modifier = Modifier.width(400.dp),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        modifier = Modifier.width(120.dp),
                        readOnly = true,
                        value = "+${selectedCountryCode}",
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            backgroundColor = DoktoRegistrationFormTextFieldBackground,
                            cursorColor = DoktoAccent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedCountryCode = selectionOption.countryCode.toString()
                                    expanded = false
                                }
                            ) {
                                Text(text = "${selectionOption.name}(${selectionOption.countryCode})")
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 150.dp)
                ) {
                    TextField(
                        value = mobileNumber.value,
                        onValueChange = {
                            mobileNumber.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(stringResource(id = R.string.hint_mobile_number)) },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            backgroundColor = DoktoRegistrationFormTextFieldBackground,
                            cursorColor = DoktoAccent,
                            placeholderColor = DoktoRegistrationFormTextFieldPlaceholder,
                            disabledPlaceholderColor = DoktoRegistrationFormTextFieldPlaceholder
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            RegistrationFormTextField(
                email,
                R.string.label_email,
                R.string.hint_email
            )
            RegistrationFormTextField(
                password,
                R.string.label_password,
                R.string.hint_password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            RegistrationFormTextField(
                confirmPassword,
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
            )
            RegistrationFormTextField(
                dateOfBirth,
                R.string.label_date_of_birth,
                R.string.hint_date_of_birth,
                onClick = {
                    datePicker.invoke { day, month, year ->
                        dateOfBirth.value = "$day-$month-$year"
                    }
                },
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {  },
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
}