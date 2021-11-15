package com.toybeth.dokto.twilio.data.rest.model.request

import com.google.gson.annotations.SerializedName

data class TwilioVideoCallRequest(
    @SerializedName("id")
    val userId: String,
    @SerializedName("room_name")
    val roomName: String
)
