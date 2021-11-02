package com.toybeth.dokto.twilio.data.rest

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import com.toybeth.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybeth.dokto.twilio.data.preferences.TwilioSharedPreference.Companion.VIDEO_CAPTURE_RESOLUTION_DEFAULT
import com.toybeth.dokto.twilio.data.preferences.TwilioSharedPreference.Companion.VIDEO_DIMENSIONS
import com.toybeth.dokto.twilio.data.rest.model.request.TwilioVideoCallAccessTokenRequestBody
import com.toybeth.dokto.twilio.data.rest.model.response.AuthServiceError
import com.toybeth.dokto.twilio.data.rest.model.response.AuthServiceException
import com.toybeth.dokto.twilio.data.rest.model.response.Topology.*
import com.toybeth.dokto.twilio.data.rest.model.response.TwilioServiceError
import com.toybeth.dokto.twilio.data.rest.model.response.TwilioVideoCallAccessTokenResponseResult
import com.twilio.video.VideoDimensions
import com.twilio.video.Vp8Codec
import retrofit2.HttpException

class TwilioAuthDataSource(
    private val apiService: TwilioRestApiDataSource,
    private val sharedPreferences: TwilioSharedPreference
) {

    suspend fun getToken(identity: String?, roomName: String?): String {
        val requestBody =
            TwilioVideoCallAccessTokenRequestBody("61986896555800", identity, roomName, true)

        try {
            apiService.getToken(requestBody).let { response ->
                return handleResponse(response)
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
            response.topology?.let { serverTopology ->
                val isTopologyChange =
                    sharedPreferences.topology != serverTopology.value
                if (isTopologyChange) {
                    sharedPreferences.topology = serverTopology.value
                    val (enableSimulcast, videoDimensionsIndex) = when (serverTopology) {
                        GROUP, GROUP_SMALL -> true to VIDEO_CAPTURE_RESOLUTION_DEFAULT
                        PEER_TO_PEER, GO -> false to VIDEO_DIMENSIONS.indexOf(VideoDimensions.HD_720P_VIDEO_DIMENSIONS)
                            .toString()
                    }
                    Logger.d(
                        "Server topology has changed to %s. Setting the codec to Vp8 with simulcast set to %s",
                        serverTopology, enableSimulcast
                    )
                    sharedPreferences.videoCodec = Vp8Codec.NAME
                    sharedPreferences.vp8SimulCast = enableSimulcast
                    sharedPreferences.videoCaptureResolution = videoDimensionsIndex
                }
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