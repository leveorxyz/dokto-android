package com.toybeth.docto.ui.features.registration.doctor.form

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.docto.data.City
import com.toybeth.docto.data.Country
import com.toybeth.docto.data.Education
import com.toybeth.docto.data.State
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
    val selectedCountry = mutableStateOf<Country?>(null)
    val selectedState = mutableStateOf<State?>(null)
    val selectedCity = mutableStateOf<City?>(null)

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

    fun addSpecialty(specialty: String) {
        specialties.add(mutableStateOf(specialty))
    }

    fun verifyFirstStep(): Boolean {

        if (userId.value.isEmpty()) {
            usedIdError.value = "This field is required"
        }

        if (name.value.isEmpty()) {
            nameError.value = "This field is required"
        }

        if (selectedCountry.value == null) {
            countryError.value = "Select country"
        }

        if (mobileNumber.value.isEmpty()) {
            mobileNumberError.value = "This field is required"
        }

        if (email.value.isEmpty()) {
            emailError.value = "This field is required"
        }

        if (password.value.isEmpty()) {
            passwordError.value = "This field is required"
        }

        if (confirmPassword.value.isEmpty()) {
            confirmPasswordError.value = "This field is required"
        } else if (
            password.value != confirmPassword.value
        ) {
            confirmPasswordError.value = "Passwords do not match"
        }

        if (dateOfBirth.value.isEmpty()) {
            dateOfBirthError.value = "This field is required"
        }

        return true || !(
            userId.value.isEmpty() ||
                name.value.isEmpty() ||
                selectedCountry.value == null ||
                mobileNumber.value.isEmpty() ||
                email.value.isEmpty() ||
                password.value.isEmpty() ||
                confirmPassword.value.isEmpty() ||
                password.value != confirmPassword.value ||
                gender.value.isEmpty() ||
                dateOfBirth.value.isEmpty()
            )
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