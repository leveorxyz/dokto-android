package com.toybethsystems.dokto.ui.features.registration.clinic

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.SingleLiveEvent
import com.toybethsystems.dokto.base.utils.extensions.isEmailValid
import com.toybethsystems.dokto.base.utils.extensions.isPasswordValid
import com.toybethsystems.dokto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybethsystems.dokto.data.Country
import com.toybethsystems.dokto.data.Property
import com.toybethsystems.dokto.data.registration.RegistrationRepository
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RegistrationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClinicRegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
): BaseViewModel() {

    companion object {
        private const val USER_TYPE = "clinic"
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
    val address = Property<String>()
    val numberOfPractitioner = Property<String>()

    val countryList = MutableLiveData<List<Country>>()
    val moveNext = SingleLiveEvent<Boolean>()

    init {
        loadCountryStateAndCities()
    }

    fun checkIfUserNameAvailable() {
        if(!userId.state.value.isNullOrEmpty()) {

            viewModelScope.launchIOWithExceptionHandler({
                val isUserExists = repository.checkIfUserNameExists(USER_TYPE, userId.state.value!!)
                if(isUserExists) {
                    userId.error.value = "Username not available"
                } else {
                    userId.error.value = null
                }
            }, {
                it.printStackTrace()
            })
        }
    }

    fun setCountry(country: Country) {
        this.country.state.value = country
    }

    fun getSelectedCountryCode(): String {
        return if (country.state.value != null) {
            country.state.value!!.phone
        } else {
            ""
        }
    }

    fun moveNext() {
        moveNext.postValue(true)
    }

    fun verifyClinicRegistrationFirstStep(): Boolean {
        var isValid = true

        if (userId.state.value.isNullOrEmpty() && userId.error.value == null) {
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

        if (address.state.value.isNullOrEmpty()) {
            address.error.value = "This field is required"
        }

        if (numberOfPractitioner.state.value.isNullOrEmpty()) {
            address.error.value = "This field is required"
        }
        return isValid
    }

    fun registerClinic() {

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