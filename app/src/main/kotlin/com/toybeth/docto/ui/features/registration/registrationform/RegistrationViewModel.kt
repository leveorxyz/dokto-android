package com.toybeth.docto.ui.features.registration.registrationform

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    private val selectedCountry = mutableStateOf<Country?>(null)
    val moveNext = SingleLiveEvent<Boolean>()
    val userId = mutableStateOf("")
    val name = mutableStateOf("")
    val mobileNumber = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val gender = mutableStateOf("")
    val dateOfBirth = mutableStateOf("")

    val selectedState = mutableStateOf<State?>(null)
    val selectedCity = mutableStateOf<City?>(null)
    val countryList = MutableLiveData<List<Country>>()
    val stateList = MutableLiveData<List<State>>()
    val cityList = MutableLiveData<List<City>>()
    val selectedStateName = mutableStateOf("")
    val selectedCityName = mutableStateOf("")

    val selectedLanguages = mutableStateListOf<String>()
    val educations = mutableStateListOf(Education())

    init {
        loadCountryStateAndCities()
    }

    fun setDateOfBirth(timeInMillis: Long) {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        dateOfBirth.value = formatter.format(Date(timeInMillis))
    }

    fun setCountry(country: Country) {
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

    fun verifyFirstStep(): Boolean {
        return !(
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
        ) || true
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