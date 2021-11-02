package com.toybeth.dokto.twilio.data.rest

import com.toybeth.docto.base.data.network.BaseResponse
import com.toybeth.dokto.twilio.data.rest.model.request.TwilioVideoCallRequest
import com.toybeth.dokto.twilio.data.rest.model.response.TwilioVideoCallAccessTokenResponseResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TwilioRestApiDataSource {
    @POST("twilio/video-token/")
    suspend fun getTwilioVideoCallAccessToken(
        @Body requestBody: TwilioVideoCallRequest
    ): Response<BaseResponse<TwilioVideoCallAccessTokenResponseResult>>
}