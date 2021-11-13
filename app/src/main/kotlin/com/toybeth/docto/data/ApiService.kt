package com.toybeth.docto.data

import com.toybeth.docto.data.registration.model.PatientRegistrationRequestBody
import io.reactivex.Maybe
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("user/patient-signup/")
    fun patientRegistration(@Body requestBody: PatientRegistrationRequestBody)
}