package com.toybethsystems.dokto.data.registration

import android.content.Context
import android.net.Uri
import androidx.compose.ui.text.toUpperCase
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.data.preference.AppPreference
import com.toybethsystems.dokto.data.*
import com.toybethsystems.dokto.base.data.model.ResultWrapper
import com.toybethsystems.dokto.base.data.network.safeApiCall
import com.toybethsystems.dokto.base.utils.fileUriToBase64
import com.toybethsystems.dokto.data.*
import com.toybethsystems.dokto.data.registration.model.DoctorRegistrationRequestBody
import com.toybethsystems.dokto.data.registration.model.EducationItemInDoctorRegistrationRequestBody
import com.toybethsystems.dokto.data.registration.model.ExperienceItemInDoctorRegistrationRequestBody
import com.toybethsystems.dokto.data.registration.model.PatientRegistrationRequestBody
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val preference: AppPreference
) {
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
        socialSecurityNumber: String? = null,
        state: String? = null,
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
            contactNo = phoneCode + contactNo,
            email = email,
            password = password,
            gender = gender.uppercase(),
            dateOfBirth = dateOfBirth,
            profilePhoto = profilePhotoString,
            identificationType = identificationType.uppercase(),
            identificationNumber = identificationNumber,
            identificationPhoto = identificationPhotoString,
            street = street,
            socialSecurityNumber = socialSecurityNumber,
            state = state,
            city = city,
            zipCode = zipCode,
            referringDoctorAddress = referringDoctorAddress,
            referringDoctorFullName = referringDoctorFullName,
            referringDoctorPhoneNumber = referringDoctorPhoneNumber,
            insuranceType = insuranceType.uppercase(),
            insuranceName = insuranceName,
            insuranceNumber = insuranceNumber,
            insurancePolicyHolderName = insurancePolicyHolderName
        )
        return when (val response = safeApiCall { apiService.patientRegistration(requestBody) }) {
            is ResultWrapper.Success -> {
                preference.user = response.value
                true
            }
            else -> false
        }
    }

    suspend fun checkIfUserNameExists(
        userType: String,
        userName: String
    ): Boolean {
        return when (safeApiCall { apiService.checkIfUserNameAvailable(userType, userName) }) {
            is ResultWrapper.Success -> true
            else -> false
        }
    }

    suspend fun registerDoctor(
        // userId: String,
        fullName: String,
        country: String,
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
        street: String? = null,
        state: String? = null,
        city: String? = null,
        zipCode: String,
        languages: List<String>,
        educationProfiles: List<Education>,
        specialities: List<String>,
        professionalBio: String,
        experiences: List<Experience>,
        licencePhotoUri: Uri,
        awards: String? = null,
        acceptedInsurances: List<String>
    ): Boolean {
        val profilePhotoString = fileUriToBase64(context, profilePhotoUri)
        val identificationPhotoString = fileUriToBase64(context, identificationPhotoUri)
        val licencePhotoString = fileUriToBase64(context, licencePhotoUri)
        val mEducations = mutableListOf<EducationItemInDoctorRegistrationRequestBody>()
        val mExperiences = mutableListOf<ExperienceItemInDoctorRegistrationRequestBody>()
        educationProfiles.forEach {
            val certificateBase64String = fileUriToBase64(context, it.certificateUri.state.value!!)
            val item = EducationItemInDoctorRegistrationRequestBody(
                college = it.college.state.value!!,
                year = it.graduationYear.state.value!!,
                certificate = certificateBase64String,
                course = it.courseStudied.state.value!!
            )
            mEducations.add(item)
        }
        experiences.forEach {
            val item = ExperienceItemInDoctorRegistrationRequestBody(
                jobDescription = it.jobDescription.state.value!!,
                jobTitle = it.jobTitle.state.value!!,
                establishmentName = it.establishmentName.state.value!!,
                startDate = it.startDate.state.value!!,
                endDate = it.endDate.state.value
            )
            mExperiences.add(item)
        }
        val requestBody = DoctorRegistrationRequestBody(
            // username = userId,
            fullName = fullName,
            contactNo = phoneCode + contactNo,
            email = email,
            password = password,
            gender = gender.uppercase(),
            dateOfBirth = dateOfBirth,
            profilePhoto = profilePhotoString,
            identificationType = identificationType.uppercase(),
            identificationNumber = identificationNumber,
            identificationPhoto = identificationPhotoString,
            street = street,
            state = state,
            city = city,
            zipCode = zipCode,
            country = country,
            specialty = specialities,
            education = mEducations,
            language = languages,
            professionalBio = professionalBio,
            experience = mExperiences,
            acceptedInsurance = acceptedInsurances,
            licenseFile = licencePhotoString,
            awards = awards
        )
        return when (val response = safeApiCall { apiService.doctorRegistration(requestBody) }) {
            is ResultWrapper.Success -> {
                preference.user = response.value
                true
            }
            else -> false
        }
    }

    // FIXME: Phone code should be merged with country in Backend
    suspend fun getCountryList(): List<Country> {
        return when (val response = safeApiCall { apiService.getCountryList() }) {
            is ResultWrapper.Success -> {
                val countryList = response.value
                val phoneCodeList = getPhoneCodeList()
                countryList.forEachIndexed { index, country ->
                    try {
                        country.phone = phoneCodeList[index].code
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        country.phone = ""
                        e.printStackTrace()
                    }
                }
                countryList
            }
            else -> listOf()
        }
    }

    suspend fun getStateList(countryCode: String): List<State> {
        return when (val response = safeApiCall { apiService.getStateList(countryCode) }) {
            is ResultWrapper.Success -> {
                response.value
            }
            else -> listOf()
        }
    }

    suspend fun getCityList(countryCode: String, stateCode: String): List<City> {
        return when (val response =
            safeApiCall { apiService.getCityList(countryCode, stateCode) }) {
            is ResultWrapper.Success -> {
                val citiesRaw = response.value
                val cites = mutableListOf<City>()
                for (city in citiesRaw) {
                    cites.add(City(city))
                }
                return cites
            }
            else -> listOf()
        }
    }

    private suspend fun getPhoneCodeList(): List<Phone> {
        return when (val response = safeApiCall { apiService.getPhoneCodeList() }) {
            is ResultWrapper.Success -> {
                response.value
            }
            else -> listOf()
        }
    }

    fun getInsuranceTypes(): List<String> {
        return context.resources.getStringArray(R.array.insurance_type).toList()
    }
}