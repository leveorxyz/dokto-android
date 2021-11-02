package com.toybeth.docto.base.data.network

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: T

)