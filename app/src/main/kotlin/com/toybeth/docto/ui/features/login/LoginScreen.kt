package com.toybeth.docto.ui.features.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.toybeth.docto.R
import com.toybeth.docto.ui.features.login.components.DoktoPasswordField
import com.toybeth.docto.ui.features.login.components.DoktoTextField
import com.toybeth.docto.ui.theme.DoktoAccent
import com.toybeth.docto.ui.theme.DoktoPrimary
import com.toybeth.docto.ui.theme.DoktoSecondary

@ExperimentalAnimationApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val loginState = loginViewModel.initializeLogin.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DoktoPrimary)
            .padding(24.dp),
        contentAlignment = Center
    ) {
        Column(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.dokto_logo),
                contentDescription = stringResource(R.string.dokto_logo_description),
                modifier = Modifier
                    .width(190.dp)
                    .align(CenterHorizontally)
            )

            AnimatedVisibility(
                visible = loginState.value == true
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(80.dp))

                    DoktoTextField(
                        value = username,
                        label = "Username",
                        placeholder = "Phone number or email",
                        icon = Icons.Outlined.Person,
                        onValueChange = { username = it },
                    )

                    Spacer(modifier = Modifier.height(32.dp))

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
                            modifier = Modifier.clickable { }
                        )

                        Text(
                            text = "register here",
                            color = DoktoAccent,
                            modifier = Modifier.clickable { }
                        )
                    }

                    Spacer(modifier = Modifier.height(56.dp))

                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .width(200.dp)
                            .align(CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = DoktoSecondary
                        )
                    ) {
                        Text(text = "Login", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}