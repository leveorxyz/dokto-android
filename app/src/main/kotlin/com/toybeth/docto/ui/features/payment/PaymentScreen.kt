package com.toybeth.docto.ui.features.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.ui.features.payment.components.selectPaymentType
import com.toybeth.docto.ui.features.payment.data.paymentTypes

@Composable
fun PaymentScreen(
    navigateToStripePayment: () -> Unit,
    navigateToFlutterwavePayment: () -> Unit
) {

    var selectedPayment by remember {
        mutableStateOf(paymentTypes[0])
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = "Payment Methods",
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))

            selectedPayment = selectPaymentType(paymentTypes)
        }

        Button(
            onClick = {
                if(selectedPayment.name.equals("stripe", true)) {
                    navigateToStripePayment()
                }
                else if(selectedPayment.name.equals("Flutterwave", true)) {
                    navigateToFlutterwavePayment()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = selectedPayment.description, fontSize = 16.sp)
        }
    }
}