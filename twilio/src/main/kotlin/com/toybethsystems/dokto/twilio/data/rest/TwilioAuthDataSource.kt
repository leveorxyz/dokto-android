package com.toybethsystems.dokto.twilio.data.rest

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import com.toybethsystems.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybethsystems.dokto.twilio.data.preferences.TwilioSharedPreference.Companion.VIDEO_CAPTURE_RESOLUTION_DEFAULT
import com.toybethsystems.dokto.twilio.data.preferences.TwilioSharedPreference.Companion.VIDEO_DIMENSIONS
import com.toybethsystems.dokto.twilio.data.rest.model.request.TwilioVideoCallAccessTokenRequestBody
import com.toybethsystems.dokto.twilio.data.rest.model.response.AuthServiceError
import com.toybethsystems.dokto.twilio.data.rest.model.response.AuthServiceException
import com.toybethsystems.dokto.twilio.data.rest.model.response.Topology.*
import com.toybethsystems.dokto.twilio.data.rest.model.response.TwilioServiceError
import com.toybethsystems.dokto.twilio.data.rest.model.response.TwilioVideoCallAccessTokenResponseResult
import com.twilio.video.VideoDimensions
import com.twilio.video.Vp8Codec
import retrofit2.HttpException

class TwilioAuthDataSource(
    private val apiService: TwilioRestApiDataSource,
    private val sharedPreferences: TwilioSharedPreference
) {

    suspend fun getToken(identity: String?, roomName: String?): String {
        val requestBody =
            TwilioVideoCallAccessTokenRequestBody("3fa85f64-5717-4562-b3fc-2c963f66afa6", roomName)

        try {
            apiService.getToken(requestBody).let { response ->
                return handleResponse(response.result)
                    ?: throw AuthServiceException(message = "Token cannot be null")
            }
        } catch (httpException: HttpException) {
            handleException(httpException)
        }
        throw IllegalArgumentException("Passcode cannot be null")
    }

    private fun handleResponse(response: TwilioVideoCallAccessTokenResponseResult): String {
        return response.token.let { token ->
            Logger.d(
                "Response successfully retrieved from the Twilio auth service: %s",
                response
            )
            val isTopologyChange =
                sharedPreferences.topology != GROUP.value
            if (isTopologyChange) {
                sharedPreferences.topology = GROUP.value
                val (enableSimulcast, videoDimensionsIndex) = true to VIDEO_CAPTURE_RESOLUTION_DEFAULT

                sharedPreferences.videoCodec = Vp8Codec.NAME
                sharedPreferences.vp8SimulCast = enableSimulcast
                sharedPreferences.videoCaptureResolution = videoDimensionsIndex
            }
            token
        }
    }

    private fun handleException(httpException: HttpException) {
        Logger.e(httpException.message(), httpException)
        httpException.response()?.let { response ->
            response.errorBody()?.let { errorBody ->
                try {
                    val errorJson = errorBody.string()
                    Gson().fromJson(errorJson, TwilioServiceError::class.java)?.let { errorDTO ->
                        errorDTO.error?.let { error ->
                            val error = AuthServiceError.value(error.message)
                            throw AuthServiceException(httpException, error)
                        }
                    }
                } catch (jsonSyntaxException: JsonSyntaxException) {
                    throw AuthServiceException(jsonSyntaxException)
                }
            }
        }
        throw AuthServiceException(httpException)
    }
}