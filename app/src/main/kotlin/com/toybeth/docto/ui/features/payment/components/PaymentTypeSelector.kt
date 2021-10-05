package com.toybeth.docto.ui.features.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toybeth.docto.ui.features.payment.data.PaymentType

@Composable
fun selectPaymentType(
    paymentOptions: List<PaymentType> = listOf()
): PaymentType {
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(paymentOptions[0])
    }

    Column(
        Modifier.padding(10.dp)
    ) {
        paymentOptions.forEach { paymentType ->
            PaymentButton(
                paymentType = paymentType,
                selected = (paymentType == selectedOption),
                onClick = { onOptionSelected(paymentType) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    return selectedOption
}