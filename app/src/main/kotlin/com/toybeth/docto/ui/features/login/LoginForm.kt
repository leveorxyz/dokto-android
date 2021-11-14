package com.toybeth.docto.ui.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.toybeth.docto.ui.features.login.components.DoktoPasswordField
import com.toybeth.docto.ui.features.login.components.DoktoTextField
import com.toybeth.docto.base.theme.DoktoSecondary
import com.toybeth.docto.base.theme.DoktoPrimaryVariant

@Composable
fun LoginForm(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier.fillMaxWidth()
) {

    var username by remember { viewModel.userNameOrPhone }
    var password by remember { viewModel.password }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        DoktoTextField(
            value = username,
            label = "Phone number or email",
            icon = Icons.Outlined.Person,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(35.dp))

        DoktoPasswordField(
            value = password,
            label = "Password",
            icon = Icons.Outlined.Lock,
            onValueChange = { password = it }
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