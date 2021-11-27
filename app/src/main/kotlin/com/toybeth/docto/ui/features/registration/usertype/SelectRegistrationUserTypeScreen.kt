package com.toybeth.docto.ui.features.registration.usertype

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.toybeth.docto.R
import com.toybeth.docto.base.theme.DoktoPrimaryVariant
import com.toybeth.docto.base.theme.DoktoUserTypeClinicColor
import com.toybeth.docto.base.theme.DoktoUserTypePharmacyColor
import com.toybeth.docto.base.theme.TextColorWhite
import com.toybeth.docto.base.ui.uiutils.getEnterAnimation
import com.toybeth.docto.base.ui.uiutils.getExitAnimation
import com.toybeth.docto.base.ui.uiutils.isVisible

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun SelectRegistrationUserTypeScreen(
    viewModel: SelectRegistrationUserTypeViewModel,
) {
    val screenAnimState = viewModel.screenAnimState.observeAsState()
    val scrollState = rememberScrollState()

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
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
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
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        horizontalAlignment =
                        Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = stringResource(id = R.string.register),
                            color = DoktoPrimaryVariant,
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
                }

                Column(
                    modifier = Modifier
                        .verticalScroll(
                            scrollState,
                            enabled = true
                        )
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(top = 50.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .clickable {
                                viewModel.navigateToNextPage(0)
                            },
                        backgroundColor = Color.White.copy(alpha = .3f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_patient),
                                    contentDescription = "patient",
                                    modifier = Modifier.height(100.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = stringResource(id = R.string.patient),
                                    fontSize = 40.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 30.dp)
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .clickable {
                                viewModel.navigateToNextPage(1)
                            },
                        backgroundColor = Color.White.copy(
                            alpha = .9f,
                            green = .1f,
                            red = .5f,
                            blue = .9f
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_doctor),
                                    contentDescription = "doctor",
                                    modifier = Modifier.height(100.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = stringResource(id = R.string.doctor),
                                    fontSize = 40.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 20.dp)
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            .clickable {
                                viewModel.navigateToNextPage(2)
                            },
                        backgroundColor = DoktoUserTypeClinicColor
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_clinic),
                                    contentDescription = "patient",
                                    modifier = Modifier
                                        .height(100.dp)
                                        .offset(x = (-30).dp),
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = stringResource(id = R.string.clinic),
                                    fontSize = 40.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 30.dp)
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                        backgroundColor = DoktoUserTypePharmacyColor
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_pharmacy),
                                    contentDescription = "pharmacy",
                                    modifier = Modifier.height(100.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = stringResource(id = R.string.pharmacy),
                                    fontSize = 35.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}