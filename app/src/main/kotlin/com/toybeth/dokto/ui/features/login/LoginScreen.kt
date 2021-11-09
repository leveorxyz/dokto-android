package com.toybeth.dokto.ui.features.login

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.toybeth.dokto.R

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
    val value by animateFloatAsState(
        targetValue = 2.2f,
        animationSpec = tween(
            durationMillis = 100,
            delayMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

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
            ) + fadeOut(targetAlpha = .3f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dokto_logo),
                        contentDescription = stringResource(R.string.dokto_logo_description),
                        modifier = Modifier.width(200.dp)
                    )
                }
                AnimatedVisibility(
                    modifier = Modifier.weight(value),
                    visible = loginFormState.value == true,
                    enter = fadeIn(initialAlpha = .3f) +
                            slideInHorizontally()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))
                        LoginForm(
                            viewModel = loginViewModel,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                }
            }
        }
    }
}