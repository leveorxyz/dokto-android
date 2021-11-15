package com.toybethsystems.dokto.base.data.model

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status_code")
    var statusCode: Int = 0
    @SerializedName("message")
    var message: String = ""
}