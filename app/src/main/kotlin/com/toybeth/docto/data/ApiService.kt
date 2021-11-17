package com.toybeth.docto.data

import com.toybeth.docto.base.data.model.ApiResponse
import com.toybeth.docto.base.data.model.DoktoUser
import com.toybeth.docto.data.authentication.model.LoginRequestBody
import com.toybeth.docto.data.registration.model.DoctorRegistrationRequestBody
import com.toybeth.docto.data.registration.model.PatientRegistrationRequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("user/login/")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<ApiResponse<DoktoUser>>

    @POST("user/patient-signup/")
    suspend fun patientRegistration(@Body requestBody: PatientRegistrationRequestBody): Response<ApiResponse<DoktoUser>>

    @GET("user/exists/{user_type}/{username}/")
    suspend fun checkIfUserNameAvailable(
        @Path("user_type") userType: String,
        @Path("username") userName: String
    ): Response<ApiResponse<Unit>>

    @POST("user/doctor-signup/")
    suspend fun doctorRegistration(@Body requestBody: DoctorRegistrationRequestBody): Response<ApiResponse<DoktoUser>>

    @GET("/constant/country/")
    suspend fun getCountryList() : Response<ApiResponse<List<Country>>>

    @GET("/constant/state/")
    suspend fun getStateList(
        @Query("country_code") countryCode: String
    ) : Response<ApiResponse<List<State>>>

    @GET("/constant/city/")
    suspend fun getCityList(
        @Query("country_code") countryCode: String,
        @Query("state_code") stateCode: String
    ) : Response<ApiResponse<List<String>>>
}