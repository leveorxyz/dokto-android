package com.toybeth.docto.ui.features.registration.registrationusertypeselection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.uiutils.getEnterAnimation
import com.toybeth.docto.base.ui.uiutils.getExitAnimation
import com.toybeth.docto.base.ui.uiutils.isVisible
import com.toybeth.docto.ui.theme.DoktoSecondary
import com.toybeth.docto.ui.theme.TextColorWhite
import com.toybeth.docto.ui.theme.UserTypeButtonSelectedColor
import com.toybeth.docto.ui.theme.UserTypeButtonUnselectedColor

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun SelectRegistrationUserTypeScreen(
    viewModel: SelectRegistrationUserTypeViewModel
) {
    val screenAnimState = viewModel.screenAnimState.observeAsState()
    val isDoctorSelected = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_splash_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        AnimatedVisibility(
            visible = screenAnimState.value?.isVisible() == true,
            enter = getEnterAnimation(animState = screenAnimState.value),
            exit = getExitAnimation(animState = screenAnimState.value)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
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
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(.6f),
                        horizontalAlignment =
                        Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = stringResource(id = R.string.register),
                            color = DoktoSecondary,
                            fontWeight = FontWeight.W700,
                            fontSize = TextUnit(24f, TextUnitType.Sp)
                        )
                        Text(
                            text = stringResource(id = R.string.as_doctor_or_patient),
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(12f, TextUnitType.Sp),
                            color = TextColorWhite,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        UserTypeButton(
                            onClick = {
                                isDoctorSelected.value = true
                            },
                            iconResourceId = R.drawable.ic_user_type_doctor,
                            textResourceId = R.string.doctor,
                            isSelected = isDoctorSelected.value
                        )
                        Spacer(modifier = Modifier.width(40.dp))
                        UserTypeButton(
                            onClick = {
                                isDoctorSelected.value = false
                            },
                            iconResourceId = R.drawable.ic_user_type_doctor,
                            textResourceId = R.string.patient,
                            isSelected = !isDoctorSelected.value
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1.2f),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {

                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = DoktoSecondary
                            ),
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.register),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
private fun UserTypeButton(
    onClick: () -> Unit,
    iconResourceId: Int,
    textResourceId: Int,
    isSelected: Boolean
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .width(120.dp)
            .height(120.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
            if (isSelected) UserTypeButtonSelectedColor else UserTypeButtonUnselectedColor,
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp),
                tint = Color.White
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(id = textResourceId),
                fontSize = TextUnit(15f, TextUnitType.Sp),
                color = Color.White
            )
        }
    }
}