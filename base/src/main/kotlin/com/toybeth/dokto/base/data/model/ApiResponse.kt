package com.toybeth.dokto.base.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("result")
    val result: T
) : com.toybeth.dokto.base.data.model.BaseResponse()
