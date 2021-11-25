package com.toybeth.docto.ui.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.base.theme.DoktoPrimaryVariant
import com.toybeth.docto.base.theme.DoktoSecondary
import com.toybeth.docto.ui.common.components.DoktoTextFiled

@Composable
fun LoginForm(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {

        DoktoTextFiled(
            textFieldValue = viewModel.userNameOrPhone.state.value ?: "",
            labelResourceId = R.string.label_phone_or_email,
            hintResourceId = R.string.hint_phone_or_email,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.label_password),
                    tint = Color.DarkGray
                )
            },
            errorMessage = viewModel.userNameOrPhone.error.value,
            onValueChange = {
                viewModel.userNameOrPhone.state.value = it
                viewModel.userNameOrPhone.error.value = null
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(35.dp))

        DoktoTextFiled(
            textFieldValue = viewModel.password.state.value ?: "",
            labelResourceId = R.string.label_password,
            hintResourceId = R.string.hint_password,
            isPasswordField = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = stringResource(id = R.string.label_password),
                    tint = Color.DarkGray
                )
            },
            onValueChange = {
                viewModel.password.state.value = it
                viewModel.password.error.value = null
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "forgot the password?",
                color = DoktoPrimaryVariant,
                modifier = Modifier.clickable {
                    viewModel.navigateToForgetPassword()
                }
            )

            Text(
                text = "register here",
                color = DoktoSecondary,
                modifier = Modifier.clickable {
                    viewModel.navigateToRegistration()
                }
            )
        }

        Spacer(modifier = Modifier.height(56.dp))

        Button(
            onClick = { viewModel.submit() },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .height(56.dp)
                .width(200.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DoktoPrimaryVariant
            )
        ) {
            Text(text = "Login", color = Color.White)
        }

        Spacer(modifier = Modifier.height(56.dp))
    }
}