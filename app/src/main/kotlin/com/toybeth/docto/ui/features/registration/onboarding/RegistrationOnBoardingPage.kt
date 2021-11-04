package com.toybeth.docto.ui.features.registration.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.toybeth.docto.R
import com.toybeth.docto.ui.theme.DoktoTextColorPrimary
import com.toybeth.docto.ui.theme.DoktoTextColorSecondary

@ExperimentalUnitApi
@Preview
@Composable
fun RegistrationOnBoardingPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_onboarding_background),
            contentDescription = "onboarding background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.img_onboarding_1),
                contentDescription = "onboarding",
                modifier = Modifier.width(350.dp)
            )
            Spacer(
                modifier = Modifier.height(70.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_title_1),
                    color = DoktoTextColorPrimary,
                    fontWeight = FontWeight(700),
                    fontSize = TextUnit(23f, TextUnitType.Sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.onboarding_text_1),
                    color = DoktoTextColorSecondary,
                    fontWeight = FontWeight(400),
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}