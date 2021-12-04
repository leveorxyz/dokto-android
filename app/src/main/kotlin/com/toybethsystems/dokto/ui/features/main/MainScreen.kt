package com.toybethsystems.dokto.ui.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainScreen(
    navigateToPayment: () -> Unit,
    navigateToVideoCall: () -> Unit,
    logout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VideoCallStartButton(navigateToVideoCall)
        PaymentPageButton(navigateToPayment)
        LogoutButton(logout)
    }
}

@Composable
private fun PaymentPageButton(
    navigateToPayment: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navigateToPayment()
            }
        ) {
            Text(text = "Payments", color = Color.White)
        }
    }
}

@Composable
private fun VideoCallStartButton(
    navigateToVideoCall: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navigateToVideoCall()
            }
        ) {
            Text(text = "Start Video Call", color = Color.White)
        }
    }
}


@Composable
private fun LogoutButton(
    logout: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                logout()
            }
        ) {
            Text(text = "Logout", color = Color.White)
        }
    }
}