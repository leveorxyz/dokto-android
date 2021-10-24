package com.toybeth.dokto.stripepayment.data

import com.google.gson.annotations.SerializedName

data class ConnectionTokenRequestBody(
    @SerializedName("secret")
    val secret: String,
    @SerializedName("object")
    val `object`: String = "terminal.connection_token"
)
