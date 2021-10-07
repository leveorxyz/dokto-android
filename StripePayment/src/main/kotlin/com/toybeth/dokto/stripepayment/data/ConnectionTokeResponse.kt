package com.toybeth.dokto.stripepayment.data

import com.google.gson.annotations.SerializedName

data class ConnectionTokeResponse(
    @SerializedName("customer")
    val customer: String,
    @SerializedName("ephemeralKey")
    val ephemeralKey: String,
    @SerializedName("paymentIntent")
    val paymentIntent: String
)
