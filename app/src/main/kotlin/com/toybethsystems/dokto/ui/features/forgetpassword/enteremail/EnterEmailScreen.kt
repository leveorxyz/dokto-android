package com.toybethsystems.dokto.ui.features.forgetpassword.enteremail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.uiutils.AnimState
import com.toybethsystems.dokto.base.ui.uiutils.getEnterAnimation
import com.toybethsystems.dokto.base.ui.uiutils.getExitAnimation
import com.toybethsystems.dokto.ui.features.login.components.DoktoTextField
import com.toybethsystems.dokto.base.theme.DoktoPrimaryVariant
import com.toybethsystems.dokto.base.theme.TextColorWhite

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun EnterEmailScreen(viewModel: ForgetPasswordEnterEmailViewModel) {

    val screenAnimState = viewModel.screenAnimState.observeAsState()
    var email by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_splash_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        AnimatedVisibility(
            visible = screenAnimState.value == AnimState.ENTER || screenAnimState.value == AnimState.POPENTER,
            enter = getEnterAnimation(animState = screenAnimState.value),
            exit = getExitAnimation(animState = screenAnimState.value)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(.05f))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Spacer(modifier = Modifier.width(24.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = null,
                            modifier = Modifier
                                .width(200.dp)
                                .weight(1f)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.forget_password),
                                color = DoktoPrimaryVariant,
                                fontWeight = FontWeight.W700,
                                fontSize = TextUnit(24f, TextUnitType.Sp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = stringResource(id = R.string.forget_password_enter_email_message),
                                textAlign = TextAlign.Center,
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                color = TextColorWhite,
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Spacer(modifier = Modifier.height(14.dp))
                                DoktoTextField(
                                    value = email,
                                    label = stringResource(id = R.string.enter_your_mail),
                                    error = "",
                                    onValueChange = { email = it },
                                )
                                Spacer(modifier = Modifier.height(64.dp))
                            }
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    viewModel.sendOtp("")
                                },
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = DoktoPrimaryVariant
                                ),
                                contentPadding = PaddingValues(30.dp, 15.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.reset_password),
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                }
                Spacer(modifier = Modifier.fillMaxHeight(.09f))
            }
        }
    }
}