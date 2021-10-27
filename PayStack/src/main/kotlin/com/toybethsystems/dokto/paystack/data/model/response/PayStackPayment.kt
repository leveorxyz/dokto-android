package com.toybethsystems.dokto.paystack.data.model.response

import com.google.gson.annotations.SerializedName

data class PayStackPayment(
    @SerializedName("authorization_url")
    val paymentUrl: String,

    @SerializedName("access_code")
    val accessCode: String,

    @SerializedName("reference")
    val reference: String
)
