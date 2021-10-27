package com.toybethsystems.dokto.stripepayment.ui.model

data class StripePaymentRelatedInitialData(
    val customerId: String,
    val ephemeralKeySecret: String,
    val paymentIntentSecret: String
)
