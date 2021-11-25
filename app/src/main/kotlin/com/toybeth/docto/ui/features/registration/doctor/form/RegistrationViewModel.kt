package com.toybeth.docto.ui.features.registration.doctor.form

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.isEmailValid
import com.toybeth.docto.base.utils.extensions.isPasswordValid
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.docto.data.*
import com.toybeth.docto.data.registration.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : BaseViewModel() {

    companion object {
        private const val USER_TYPE = "doctor"
    }

    // ... First Screen
    val profileImage = Property<Bitmap>()
    val profileImageUri = Property<Uri>()
    val userId = Property<String>()
    val name = Property<String>()
    val country = Property<Country>()
    val mobileNumber = Property<String>()
    val email = Property<String>()
    val password = Property<String>()
    val confirmPassword = Property<String>()
    val gender = Property<String>()
    val dateOfBirth = Property<String>()

    val moveNext = SingleLiveEvent<Boolean>()

    // ... Second Screen
    val selectedIdentification = Property<String>()
    val identificationNumber = Property<String>()
    val identityValidityImageUri = Property<Uri>()
    val address = Property<String>()
    val selectedCountryName = Property<String>()
    val selectedStateName = Property<String>()
    val selectedCityName = Property<String>()
    val zipCode = Property<String>()

    val countryList = MutableLiveData<List<Country>>()
    val stateList = MutableLiveData<List<State>>()
    val cityList = MutableLiveData<List<City>>()

    // ... Third Screen
    val selectedLanguages = Property(
        state = mutableStateOf(mutableStateListOf<String>())
    )
    val educations = Property(state = mutableStateOf(mutableListOf(Education())))
    val specialties = Property(
        state = mutableStateOf(mutableStateListOf<String>())
    )

    // ... Fourth Screen
    val professionalBio = Property<String>()
    val experiences = Property<MutableList<Experience>>(
        state = mutableStateOf(mutableStateListOf(Experience()))
    )
    val doctorLicense = Property<Bitmap?>()
    val doctorLicenseUri = Property<Uri>()
    val doctorAwards = Property<String>()
    val doctorInsurances = Property(
        state = mutableStateOf(mutableStateListOf<String>())
    )

    init {
        loadCountryList()
    }

    fun checkIfUserNameAvailable() {
        if (!userId.state.value.isNullOrEmpty()) {

            viewModelScope.launchIOWithExceptionHandler({
                val isUserExists = repository.checkIfUserNameExists(USER_TYPE, userId.state.value!!)
                if (isUserExists) {
                    userId.error.value = "Username not available"
                } else {
                    userId.error.value = null
                }
            }, {
                it.printStackTrace()
            })
        }
    }

    fun setDateOfBirth(timeInMillis: Long) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        dateOfBirth.state.value = formatter.format(Date(timeInMillis))
    }

    fun setCountry(country: Country) {
        selectedCountryName.state.value = country.name
        this.country.state.value = country
        getStateList(country.code)
    }

    fun setState(state: State) {
        selectedStateName.state.value = state.name
        country.state.value?.let {
            getCityList(it.code, state.code)
        }
    }

    fun setCity(city: City) {
        selectedCityName.state.value = city.name
    }

    fun getSelectedCountryPhoneCode(): String {
        return if (country.state.value != null) {
            val phoneCode = country.state.value!!.phone
            return if (phoneCode.startsWith("+")) {
                phoneCode
            } else {
                "+$phoneCode"
            }
        } else {
            ""
        }
    }

    fun addEducation() {
        educations.state.value?.add(Education())
    }

    fun addExperience() {
        experiences.state.value?.add(Experience())
        experiences.error.value = null
    }

    fun getGraduationYearFromMillis(timeInMillis: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return formatter.format(Date(timeInMillis))
    }

    fun addSpecialty(specialty: String) {
        specialties.state.value?.add(specialty)
        specialties.error.value = null
    }

    fun addInsurance(insurance: String) {
        doctorInsurances.state.value?.add(insurance)
        doctorInsurances.error.value = null
    }

    fun getExperienceDateFromMillis(timeInMillis: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return formatter.format(Date(timeInMillis))
    }

    fun verifyDoctorRegistrationFirstStep(): Boolean {
        var isValid = true

        if (profileImageUri.state.value == null) {
            profileImageUri.error.value = "Select profile photo"
            isValid = false
        }

//        if (userId.state.value.isNullOrEmpty() && userId.error.value == null) {
//            userId.error.value = "This field is required"
//            isValid = false
//        }

        if (name.state.value.isNullOrEmpty()) {
            name.error.value = "This field is required"
            isValid = false
        }

        if (country.state.value == null) {
            country.error.value = "Select country"
            isValid = false
        }

        if (mobileNumber.state.value.isNullOrEmpty()) {
            mobileNumber.error.value = "This field is required"
            isValid = false
        }

        if (email.state.value.isNullOrEmpty()) {
            email.error.value = "This field is required"
            isValid = false
        } else if (!email.state.value.isEmailValid()) {
            email.error.value = "Invalid email address"
            isValid = false
        }

        if (password.state.value.isNullOrEmpty()) {
            password.error.value = "This field is required"
            isValid = false
        } else if (!password.state.value.isPasswordValid()) {
            password.error.value =
                "Password must be 8 characters long and must have minimum 1 number and 1 letter"
            isValid = false
        }

        if (confirmPassword.state.value.isNullOrEmpty()) {
            confirmPassword.error.value = "This field is required"
            isValid = false
        } else if (password.state.value != confirmPassword.state.value) {
            confirmPassword.error.value = "Passwords do not match"
            isValid = false
        }

        if (dateOfBirth.state.value.isNullOrEmpty()) {
            dateOfBirth.error.value = "This field is required"
            isValid = false
        }

        return isValid
    }

    fun verifyDoctorRegistrationSecondStep(): Boolean {
        var isValid = true

        if (selectedIdentification.state.value.isNullOrEmpty()) {
            selectedIdentification.error.value = "This field is required"
            isValid = false
        }

        if (identificationNumber.state.value.isNullOrEmpty()) {
            identificationNumber.error.value = "This field is required"
            isValid = false
        }

        if (zipCode.state.value.isNullOrEmpty()) {
            zipCode.error.value = "This field is required"
            isValid = false
        }

        if (address.state.value.isNullOrEmpty()) {
            address.error.value = "This field is required"
            isValid = false
        }

        if (stateList.value?.isNullOrEmpty() == false && selectedStateName.state.value.isNullOrEmpty()) {
            selectedStateName.error.value = "Select your state"
            isValid = false
        }

        if (cityList.value?.isNullOrEmpty() == false && selectedCityName.state.value.isNullOrEmpty()) {
            selectedCityName.error.value = "Select your city"
            isValid = false
        }

        return isValid
    }

    fun verifyDoctorRegistrationThirdPage(): Boolean {
        var isValid = true
        if (selectedLanguages.state.value?.isNullOrEmpty() == true) {
            selectedLanguages.error.value = "Select minimum 1 language"
            isValid = false
        }
        educations.state.value?.forEach {
            if (it.college.state.value.isNullOrEmpty()) {
                it.college.error.value = "This field is required"
                isValid = false
            }
            if (it.graduationYear.state.value.isNullOrEmpty()) {
                it.graduationYear.error.value = "This field is required"
                isValid = false
            }
            if (it.courseStudied.state.value.isNullOrEmpty()) {
                it.courseStudied.error.value = "This field is required"
                isValid = false
            }
            if (it.certificateUri.state.value == null) {
                it.certificateUri.error.value = "This field is required"
                isValid = false
            }
        }
        if (specialties.state.value?.isNullOrEmpty() == true) {
            specialties.error.value = "This field is required"
            isValid = false
        }
        return isValid
    }

    fun verifyDoctorRegistrationFourthPage(): Boolean {
        var isValid = true
        if (professionalBio.state.value.isNullOrEmpty()) {
            professionalBio.error.value = "This field is required"
            isValid = false
        }
        experiences.state.value?.forEach {
            if (it.establishmentName.state.value.isNullOrEmpty()) {
                isValid = false
                it.establishmentName.error.value = "This field is required"
            }
            if (it.jobTitle.state.value.isNullOrEmpty()) {
                isValid = false
                it.jobTitle.error.value = "This field is required"
            }
            if (it.startDate.state.value.isNullOrEmpty()) {
                isValid = false
                it.startDate.error.value = "This field is required"
            }
        }
        if (doctorLicenseUri.state.value == null) {
            isValid = false
            doctorLicenseUri.error.value = "Upload your license photo"
        }
        if (doctorInsurances.state.value.isNullOrEmpty()) {
            isValid = false
            doctorInsurances.error.value = "Select minimum 1 insurance"
        }
        return isValid
    }

    fun moveNext() {
        moveNext.postValue(true)
    }

    fun registerDoctor() {
        loader.postValue(true)
        viewModelScope.launchIOWithExceptionHandler({
            repository.registerDoctor(
                userId = userId.state.value!!,
                fullName = name.state.value!!,
                country = country.state.value!!.name,
                phoneCode = country.state.value!!.phone,
                contactNo = mobileNumber.state.value!!,
                email = email.state.value!!,
                password = password.state.value!!,
                gender = gender.state.value!!,
                dateOfBirth = dateOfBirth.state.value!!,
                profilePhotoUri = profileImageUri.state.value!!,
                identificationType = selectedIdentification.state.value!!,
                identificationNumber = identificationNumber.state.value!!,
                identificationPhotoUri = profileImageUri.state.value!!,
                street = address.state.value!!,
                state = selectedStateName.state.value,
                city = selectedCityName.state.value,
                zipCode = zipCode.state.value!!,
                languages = selectedLanguages.state.value!!,
                educationProfiles = educations.state.value!!,
                specialities = specialties.state.value!!,
                professionalBio = professionalBio.state.value!!,
                experiences = experiences.state.value!!,
                licencePhotoUri = doctorLicenseUri.state.value!!,
                awards = doctorAwards.state.value,
                acceptedInsurances = doctorInsurances.state.value!!
            )
            loader.postValue(false)
        }, {
            loader.postValue(false)
            it.printStackTrace()
        })
    }

    private fun loadCountryList() {
        viewModelScope.launchIOWithExceptionHandler({
            val countries = repository.getCountryList()
            countryList.postValue(countries)
        }, {
            it.printStackTrace()
        })
    }

    private fun getStateList(countryCode: String) {
        viewModelScope.launchIOWithExceptionHandler({
            val states = repository.getStateList(countryCode)
            stateList.postValue(states)
        }, {
            it.printStackTrace()
        })
    }

    private fun getCityList(countryCode: String, stateCode: String) {
        viewModelScope.launchIOWithExceptionHandler({
            val cities = repository.getCityList(countryCode, stateCode)
            cityList.postValue(cities)
        }, {
            it.printStackTrace()
        })
    }
}