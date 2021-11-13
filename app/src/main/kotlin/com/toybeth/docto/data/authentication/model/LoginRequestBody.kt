package com.toybeth.docto.data.authentication.model

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
