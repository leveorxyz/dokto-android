package com.toybethsystems.dokto.twilio.data.rest.model.response

import com.google.gson.annotations.SerializedName

data class TwilioVideoCallAccessTokenResponseResult(
    @SerializedName("token")
    val token: String
)