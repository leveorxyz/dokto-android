package com.toybeth.docto.data.registration

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.toybeth.docto.R
import com.toybeth.docto.base.data.model.ResultWrapper
import com.toybeth.docto.base.data.network.safeApiCall
import com.toybeth.docto.base.data.preference.AppPreference
import com.toybeth.docto.base.utils.fileUriToBase64
import com.toybeth.docto.base.utils.getFile
import com.toybeth.docto.data.ApiService
import com.toybeth.docto.data.Country
import com.toybeth.docto.data.registration.model.PatientRegistrationRequestBody
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val preference: AppPreference
) {
    suspend fun getCountryStateCityList(): List<Country> {
        try {
            InputStreamReader(
                context.resources.openRawResource(R.raw.country_state_city)
            ).use { reader ->
                val countryListType = object : TypeToken<ArrayList<Country>>() {}.type
                val result: List<Country> = Gson().fromJson(reader, countryListType)
                return result
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return listOf()
        }
    }

    suspend fun registerPatient(
        fullName: String,
        phoneCode: String,
        contactNo: String,
        email: String,
        password: String,
        gender: String,
        dateOfBirth: String,
        profilePhotoUri: Uri,
        identificationType: String,
        identificationNumber: String,
        identificationPhotoUri: Uri,
        street: String,
        socialSecurityNumber: String,
        state: String,
        city: String? = null,
        zipCode: String,
        referringDoctorAddress: String? = null,
        referringDoctorFullName: String? = null,
        referringDoctorPhoneNumber: String? = null,
        insuranceType: String,
        insuranceName: String? = null,
        insuranceNumber: String? = null,
        insurancePolicyHolderName: String? = null,

    ): Boolean {
        val profilePhotoString = fileUriToBase64(context, profilePhotoUri)
        val identificationPhotoString = fileUriToBase64(context, identificationPhotoUri)
        val requestBody = PatientRegistrationRequestBody(
            fullName = fullName,
            contactNo =  phoneCode + contactNo,
            email = email,
            password = password,
            gender = gender,
            dateOfBirth = dateOfBirth,
            profilePhoto = profilePhotoString,
            identificationType = identificationType,
            identificationNumber = identificationNumber,
            identificationPhoto = identificationPhotoString,
            street = street,
            socialSecurityNumber = socialSecurityNumber,
            state = state,
            city = city,
            zipCode = zipCode,
            referringDoctorAddress = referringDoctorAddress,
            referringDoctorFullName =  referringDoctorFullName,
            referringDoctorPhoneNumber = referringDoctorPhoneNumber,
            insuranceType = insuranceType,
            insuranceName = insuranceName,
            insuranceNumber =  insuranceNumber,
            insurancePolicyHolderName = insurancePolicyHolderName
        )
        val response = safeApiCall { apiService.patientRegistration(requestBody) }
        return when (response) {
            is ResultWrapper.Success -> {
                preference.user = response.value
                true
            }
            else -> false
        }
    }
}