package com.toybeth.docto.ui.features.registration.doctor.form

import android.graphics.Bitmap
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
class RegistrationViewModel @Inject constructor(private val repository: RegistrationRepository) :
    BaseViewModel() {

    val moveNext = SingleLiveEvent<Boolean>()
    val userId = mutableStateOf("")
    val name = mutableStateOf("")
    val mobileNumber = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val gender = mutableStateOf("")
    val dateOfBirth = mutableStateOf("")

    val identificationNumber = mutableStateOf("")
    val address = mutableStateOf("")
    val zipCode = mutableStateOf("")
    private val selectedCountry = mutableStateOf<Country?>(null)
    private val selectedState = mutableStateOf<State?>(null)
    private val selectedCity = mutableStateOf<City?>(null)

    val countryList = MutableLiveData<List<Country>>()
    val stateList = MutableLiveData<List<State>>()
    val cityList = MutableLiveData<List<City>>()

    val selectedCountryName = mutableStateOf("")
    val selectedStateName = mutableStateOf("")
    val selectedCityName = mutableStateOf("")

    val selectedLanguages = mutableStateListOf<String>()
    val educations = mutableStateListOf(Education())

    val usedIdError = mutableStateOf<String?>(null)
    val nameError = mutableStateOf<String?>(null)
    val countryError = mutableStateOf<String?>(null)
    val mobileNumberError = mutableStateOf<String?>(null)
    val emailError = mutableStateOf<String?>(null)
    val passwordError = mutableStateOf<String?>(null)
    val confirmPasswordError = mutableStateOf<String?>(null)
    val dateOfBirthError = mutableStateOf<String?>(null)

    val identificationNumberError = mutableStateOf<String?>(null)
    val addressError = mutableStateOf<String?>(null)
    val countryNameError = mutableStateOf<String?>(null)
    val stateNameError = mutableStateOf<String?>(null)
    val cityNameError = mutableStateOf<String?>(null)
    val zipCodeError = mutableStateOf<String?>(null)

    val specialties = mutableStateListOf(mutableStateOf<String?>(null))

    val professionalBio = mutableStateOf("")
    val professionalBioError = mutableStateOf<String?>(null)
    val experiences = mutableStateListOf(Experience())
    val doctorLicense = mutableStateOf<Bitmap?>(null)
    val doctorLicenseError = mutableStateOf<String?>(null)
    val doctorAwards = mutableStateOf("")
    val doctorAwardsError = mutableStateOf<String?>(null)
    val doctorInsurances = mutableStateListOf(mutableStateOf<String?>(null))
    val doctorInsurancesError = mutableStateOf<String?>(null)

    init {
        loadCountryStateAndCities()
    }

    fun setDateOfBirth(timeInMillis: Long) {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        dateOfBirth.value = formatter.format(Date(timeInMillis))
    }

    fun setCountry(country: Country) {
        selectedCountryName.value = country.name
        selectedCountry.value = country
        stateList.postValue(country.states)
    }

    fun setState(state: State) {
        selectedStateName.value = state.name
        selectedState.value = state
        cityList.postValue(state.cities)
    }

    fun setCity(city: City) {
        selectedCityName.value = city.name
        selectedCity.value = city
    }

    fun getSelectedCountryCode(): String {
        return if (selectedCountry.value != null) {
            selectedCountry.value!!.phone
        } else {
            ""
        }
    }

    fun addEducation() {
        educations.add(Education())
    }

    fun addExperience() {
        experiences.add(Experience())
    }

    fun addSpecialty(specialty: String) {
        specialties.add(mutableStateOf(specialty))
    }

    fun addInsurance(insurance: String) {
        doctorInsurances.add(mutableStateOf(insurance))
    }

    fun verifyDoctorRegistrationFirstStep(): Boolean {
        var isValid = true
        usedIdError.value = null
        nameError.value = null
        countryError.value = null
        mobileNumberError.value = null
        emailError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
        dateOfBirthError.value = null

        if (userId.value.isEmpty()) {
            usedIdError.value = "This field is required"
            isValid = false
        }

        if (name.value.isEmpty()) {
            nameError.value = "This field is required"
            isValid = false
        }

        if (selectedCountry.value == null) {
            countryError.value = "Select country"
            isValid = false
        }

        if (mobileNumber.value.isEmpty()) {
            mobileNumberError.value = "This field is required"
            isValid = false
        }

        if (!email.value.isEmailValid()) {
            emailError.value = "This field is required"
            isValid = false
        }

        if (!password.value.isPasswordValid()) {
            passwordError.value = "This field is required"
            isValid = false
        }

        if (!confirmPassword.value.isPasswordValid()) {
            confirmPasswordError.value = "This field is required"
            isValid = false
        } else if( password.value != confirmPassword.value) {
            confirmPasswordError.value = "Passwords do not match"
            isValid = false
        }

        if (dateOfBirth.value.isEmpty()) {
            dateOfBirthError.value = "This field is required"
            isValid = false
        }

        return isValid
    }

    fun verifyDoctorRegistrationSecondStep(): Boolean {
        var isValid = true
        identificationNumberError.value = null
        zipCodeError.value = null
        addressError.value = null
        stateNameError.value = null
        cityNameError.value = null


        if (identificationNumber.value.isEmpty()) {
            identificationNumberError.value = "This field is required"
            isValid = false
        }

        if (zipCode.value.isEmpty()) {
            zipCodeError.value = "This field is required"
            isValid = false
        }

        if (address.value.isEmpty()) {
            addressError.value = "This field is required"
            isValid = false
        }

        if (stateList.value?.isNullOrEmpty() == false && selectedStateName.value.isEmpty()) {
            stateNameError.value = "Select your state"
            isValid = false
        }

        if (cityList.value?.isNullOrEmpty() == false && selectedCityName.value.isEmpty()) {
            cityNameError.value = "Select your city"
            isValid = false
        }

        return isValid
    }

    fun moveNext() {
        moveNext.postValue(true)
    }

    private fun loadCountryStateAndCities() {
        viewModelScope.launchIOWithExceptionHandler({
            val countries = repository.getCountryStateCityList()
            countryList.postValue(countries)
        }, {
            it.printStackTrace()
        })
    }
}