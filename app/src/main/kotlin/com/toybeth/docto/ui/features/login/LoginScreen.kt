package com.toybeth.docto.ui.features.login

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R

@ExperimentalAnimationApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val loginFormState = loginViewModel.initializeLoginForm.observeAsState()
    val loginScreenState = loginViewModel.initializeLoginScreen.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_splash_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        AnimatedVisibility(
            visible = loginScreenState.value == true,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally(
                targetOffsetX = { -screenWidth.value.toInt() }
            ) + fadeOut(.3f)
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
                        modifier = Modifier.width(190.dp)
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
                        visible = loginFormState.value == true,
                        enter = fadeIn(initialAlpha = .3f) +
                                slideInHorizontally()
                    ) {
                        LoginForm(loginViewModel)
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
}