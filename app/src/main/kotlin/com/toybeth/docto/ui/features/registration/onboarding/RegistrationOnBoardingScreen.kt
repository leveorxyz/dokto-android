package com.toybeth.docto.ui.features.registration.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.toybeth.docto.ui.theme.DoktoPrimary
import com.toybeth.docto.ui.theme.DoktoTextColorSecondary

@ExperimentalUnitApi
@ExperimentalPagerApi
@Composable
fun RegistrationOnBoardingScreen() {
    val currentPage = remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = 3,
        ) {
            currentPage.value = this.currentPage
            RegistrationOnBoardingPage()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WormPageIndicator(
                totalPages = 3,
                currentPage = currentPage.value,
                color = DoktoTextColorSecondary
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}