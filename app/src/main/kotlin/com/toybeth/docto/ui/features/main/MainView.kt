package com.toybeth.docto.ui.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun mainView(
    navigateToPayment: () -> Unit,
    navigateToMap: () -> Unit,
    navigateToVideoCall: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                navigateToPayment()
            },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Go to payment", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigateToMap()
            },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Open map", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigateToVideoCall()
            },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Start video call", fontSize = 16.sp)
        }
    }
}