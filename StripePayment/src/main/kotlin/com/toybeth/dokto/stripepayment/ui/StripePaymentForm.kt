package com.toybeth.dokto.stripepayment.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.dokto.stripepayment.R

@Preview
@Composable
fun stripePaymentForm() {
    val cardNumber = remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.9f)
        ) {
            Text(
                text = stringResource(id = R.string.pay_s_using, arrayListOf("500")),
                color = Color.Black,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(Dp(10f)))
            Text(
                text = stringResource(id = R.string.card_information),
                color = Color.Gray,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(Dp(5f)))
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    stripePaymentTextField(
                        onValueChange = {
                            cardNumber.value = it
                        },
                        placeholderText = stringResource(id = R.string.card_number_placeholder)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            stripePaymentTextField(
                                onValueChange = {
                                    cardNumber.value = it
                                },
                                placeholderText = stringResource(id = R.string.card_number_placeholder)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .width(1.dp)
                                .background(Color.LightGray)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            stripePaymentTextField(
                                onValueChange = {
                                    cardNumber.value = it
                                },
                                placeholderText = stringResource(id = R.string.card_number_placeholder)
                            )
                        }
                    }
                }
            }
        }
    }
}