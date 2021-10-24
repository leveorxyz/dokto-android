package com.toybeth.dokto.paystack.data.model.response

import com.google.gson.annotations.SerializedName

data class InitializeTransactionResponse(
    @SerializedName("data")
    val data: PayStackPayment
): BasePayStackApiResponse()