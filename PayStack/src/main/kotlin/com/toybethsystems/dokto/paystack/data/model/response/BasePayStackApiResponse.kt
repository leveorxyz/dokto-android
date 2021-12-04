package com.toybethsystems.dokto.paystack.data.model.response

import com.google.gson.annotations.SerializedName

open class BasePayStackApiResponse {
    @SerializedName("status")
    val status: Boolean = true

    @SerializedName("message")
    val message: String = ""
}
