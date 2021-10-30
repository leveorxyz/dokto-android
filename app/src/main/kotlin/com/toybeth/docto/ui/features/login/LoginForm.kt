package com.toybeth.docto.ui.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toybeth.docto.ui.features.login.components.DoktoPasswordField
import com.toybeth.docto.ui.features.login.components.DoktoTextField
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoSecondary

@Composable
fun LoginForm(
    viewModel: LoginViewModel
) {

    var username by remember { viewModel.userNameOrPhone }
    var password by remember { viewModel.password }

    Column(
        modifier = Modifier.aspectRatio(1.0f),
        verticalArrangement = Arrangement.Center
    ) {
        DoktoTextField(
            value = username,
            label = "Username",
            placeholder = "Phone number or email",
            icon = Icons.Outlined.Person,
            onValueChange = { username = it },
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
                color = DoktoSecondary,
                modifier = Modifier.clickable {
                    viewModel.navigateToForgetPassword()
                }
            )

            Text(
                text = "register here",
                color = DoktoAccent,
                modifier = Modifier.clickable { }
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
                backgroundColor = DoktoSecondary
            )
        ) {
            Text(text = "Login", color = Color.White)
        }
    }
}