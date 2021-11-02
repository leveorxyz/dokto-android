package com.toybeth.dokto.twilio.data.rest.model.request

data class TwilioVideoCallAccessTokenRequestBody(
    val passcode: String? = null,
    val user_identity: String? = null,
    val room_name: String? = null,
    val create_room: Boolean = false
)