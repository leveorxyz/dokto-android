package com.toybeth.docto.ui.features.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.theme.DoktoPrimary

@ExperimentalAnimationApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel
) {

    val loginState = loginViewModel.initializeLogin.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DoktoPrimary)
            .padding(24.dp),
        contentAlignment = Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .height(Dp.Infinity)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dokto_logo),
                    contentDescription = stringResource(R.string.dokto_logo_description),
                    modifier = Modifier
                        .width(190.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(Dp.Infinity)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = loginState.value == true
                ) {
                    LoginForm()
                }
            }
            Row(
                modifier = Modifier
                    .height(Dp.Infinity)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

            }
        }
    }
}