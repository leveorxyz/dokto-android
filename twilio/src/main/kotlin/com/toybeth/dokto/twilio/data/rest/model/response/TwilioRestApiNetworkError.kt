package com.toybeth.dokto.twilio.data.rest.model.response

import com.orhanobut.logger.Logger

data class TwilioServiceError(
    val error: TwilioErrorResult? = null
)

data class TwilioErrorResult(
    val message: String? = null,
    val explanation: String? = null
)

enum class AuthServiceError {
    INVALID_PASSCODE_ERROR,
    EXPIRED_PASSCODE_ERROR;

    companion object {
        fun value(value: String?): AuthServiceError? =
            when (value) {
                "passcode incorrect" -> INVALID_PASSCODE_ERROR
                "passcode expired" -> EXPIRED_PASSCODE_ERROR
                else -> {
                    Logger.d("Unrecognized Auth Service error message: %s", value)
                    null
                }
            }
    }
}

class AuthServiceException(
    throwable: Throwable? = null,
    val error: AuthServiceError? = null,
    message: String? = null
) : RuntimeException(message, throwable)