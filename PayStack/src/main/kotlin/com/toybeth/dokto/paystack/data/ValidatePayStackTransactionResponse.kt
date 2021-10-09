package com.toybeth.dokto.paystack.data

import com.google.gson.annotations.SerializedName

data class ValidatePayStackTransactionResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String
)
