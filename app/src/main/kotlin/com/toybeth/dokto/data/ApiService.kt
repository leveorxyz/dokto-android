package com.toybeth.dokto.data

import com.toybeth.dokto.base.data.model.ApiResponse
import com.toybeth.dokto.base.data.model.BaseResponse
import com.toybeth.dokto.base.data.model.DoktoUser
import com.toybeth.dokto.data.authentication.model.LoginRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("user/login/")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<com.toybeth.dokto.base.data.model.ApiResponse<com.toybeth.dokto.base.data.model.DoktoUser>>
}