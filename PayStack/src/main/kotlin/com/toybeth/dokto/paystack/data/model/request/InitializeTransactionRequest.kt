package com.toybeth.dokto.paystack.data.model.request

import com.google.gson.annotations.SerializedName

data class InitializeTransactionRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("amount")
    val amount: String
)