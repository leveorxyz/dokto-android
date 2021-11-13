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
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : BaseViewModel() {

    // ... First Screen
    val profileImage = Property<Bitmap>()
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
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        dateOfBirth.state.value = formatter.format(Date(timeInMillis))
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

        if (userId.state.value.isNullOrEmpty()) {
            userId.error.value = "This field is required"
            isValid = false
        }

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
        }

        if (email.state.value.isEmailValid()) {
            email.error.value = "Invalid email address"
            isValid = false
        }

        if (!password.state.value.isPasswordValid()) {
            password.error.value = "This field is required"
        }

        if (!confirmPassword.state.value.isPasswordValid()) {
            confirmPassword.error.value = "This field is required"
        } else if (
            password.state.value != confirmPassword.state.value
        ) {
            confirmPassword.error.value = "Passwords do not match"
        }

        if (dateOfBirth.state.value.isNullOrEmpty()) {
            dateOfBirth.error.value = "This field is required"
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