package com.toybethsystems.dokto.ui.features.payment.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toybethsystems.dokto.ui.features.payment.data.PaymentType
import com.toybethsystems.dokto.ui.theme.Purple700

@Composable
fun PaymentButton(paymentType: PaymentType, selected: Boolean, onClick: () -> Unit) {

    var border: BorderStroke? by remember {
        mutableStateOf(BorderStroke(1.dp, Color.LightGray))
    }

    border = if (selected) {
        BorderStroke(3.dp, Purple700)
    } else {
        BorderStroke(1.dp, Color.LightGray)
    }

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp), onClick = { onClick() },
        border = border,
        shape = RoundedCornerShape(50)
    ) {
        Image(
            modifier = Modifier.padding(12.dp),
            painter = painterResource(id = paymentType.logo),
            contentDescription = paymentType.description
        )
    }
}