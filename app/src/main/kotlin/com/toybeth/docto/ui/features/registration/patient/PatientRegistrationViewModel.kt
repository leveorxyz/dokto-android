package com.toybeth.docto.ui.features.registration.patient

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.data.City
import com.toybeth.docto.data.Country
import com.toybeth.docto.data.Property
import com.toybeth.docto.data.State
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PatientRegistrationViewModel @Inject constructor() : BaseViewModel(){

    val countryList = MutableLiveData<List<Country>>()
    val stateList = MutableLiveData<List<State>>()
    val cityList = MutableLiveData<List<City>>()

    // ... First Screen
    val profileImage = Property<Bitmap>()
    val firstName = Property<String>()
    val lastName = Property<String>()
    val country = Property<Country>()
    val mobileNumber = Property<String>()
    val email = Property<String>()
    val password = Property<String>()
    val confirmPassword = Property<String>()
    val gender = Property<String>()
    val dateOfBirth = Property<String>()

    // Second Screen
    val identityVerificationNumber = Property<String>()
    val socialSecurityNumber = Property<String>()
    val address = Property<String>()
    val stateName = Property<String>()
    val cityName = Property<String>()
    val zipCode = Property<String>()

    private val selectedCountry = mutableStateOf<Country?>(null)
    private val selectedState = mutableStateOf<State?>(null)
    private val selectedCity = mutableStateOf<City?>(null)

    val moveNext = SingleLiveEvent<Boolean>()

    fun setDateOfBirth(timeInMillis: Long) {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
        dateOfBirth.state.value = formatter.format(Date(timeInMillis))
    }

    fun setCountry(country: Country) {
        selectedCountry.value = country
        stateList.postValue(country.states)
    }

    fun setState(state: State) {
        stateName.state.value = state.name
        selectedState.value = state
        cityList.postValue(state.cities)
    }

    fun setCity(city: City) {
        cityName.state.value = city.name
        selectedCity.value = city
    }

    fun getSelectedCountryCode(): String {
        return if (selectedCountry.value != null) {
            selectedCountry.value!!.phone
        } else {
            ""
        }
    }

    fun moveNext() {
        moveNext.postValue(true)
    }

}