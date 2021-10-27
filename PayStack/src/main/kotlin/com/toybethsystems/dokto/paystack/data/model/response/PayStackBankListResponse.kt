package com.toybethsystems.dokto.paystack.data.model.response

import com.google.gson.annotations.SerializedName

data class PayStackBankListResponse(
    @SerializedName("data")
    val bankList: List<PayStackSupportedBank>
): BasePayStackApiResponse()
