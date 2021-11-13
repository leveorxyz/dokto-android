package com.toybeth.docto.data

import com.toybeth.docto.base.data.model.ApiResponse
import com.toybeth.docto.base.data.model.BaseResponse
import com.toybeth.docto.base.data.model.DoktoUser
import com.toybeth.docto.data.authentication.model.LoginRequestBody
import com.toybeth.docto.data.registration.model.PatientRegistrationRequestBody
import io.reactivex.Maybe
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("user/login/")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<ApiResponse<DoktoUser>>

    @POST("user/patient-signup/")
    suspend fun patientRegistration(@Body requestBody: PatientRegistrationRequestBody): Response<ApiResponse<DoktoUser>>
}