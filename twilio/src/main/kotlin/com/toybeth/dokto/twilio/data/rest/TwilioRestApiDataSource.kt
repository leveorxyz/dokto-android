package com.toybeth.dokto.twilio.data.rest

import com.toybeth.docto.base.data.network.BaseResponse
import com.toybeth.dokto.twilio.data.rest.model.request.TwilioVideoCallAccessTokenRequestBody
import com.toybeth.dokto.twilio.data.rest.model.request.TwilioVideoCallRequest
import com.toybeth.dokto.twilio.data.rest.model.response.TwilioVideoCallAccessTokenResponseResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TwilioRestApiDataSource {
    @POST("token")
    suspend fun getToken(
        @Body requestBody: TwilioVideoCallAccessTokenRequestBody
    ): TwilioVideoCallAccessTokenResponseResult
}