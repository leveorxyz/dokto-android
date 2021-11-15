package com.toybethsystems.dokto.ui.features.payment.data

import com.toybethsystems.dokto.R

data class PaymentType(
    val name: String,
    val description: String,
    val logo: Int
)

val paymentTypes = listOf(
    PaymentType(
        name = "PayPal",
        description = "Pay with PayPal",
        logo = R.drawable.paypal_logo
    ),

    PaymentType(
        name = "Flutterwave",
        description = "Pay with Flutterwave",
        logo = R.drawable.flutterwave_logo
    ),

    PaymentType(
        name = "Stripe",
        description = "Pay with Stripe",
        logo = R.drawable.stripe_logo
    ),

    PaymentType(
        name = "Paystack",
        description = "Pay with Paystack",
        logo = R.drawable.paystack_logo
    ),
)