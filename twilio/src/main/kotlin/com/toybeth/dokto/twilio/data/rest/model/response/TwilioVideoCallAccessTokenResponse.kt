package com.toybeth.dokto.twilio.data.rest.model.response

import com.google.gson.annotations.SerializedName

data class TwilioVideoCallAccessTokenResponseResult(
    @SerializedName("token")
    val token: String,
    @SerializedName("room_type") val topology: Topology? = null
)