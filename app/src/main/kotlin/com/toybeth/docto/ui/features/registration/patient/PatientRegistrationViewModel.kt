package com.toybeth.docto.ui.features.registration.patient

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.isEmailValid
import com.toybeth.docto.base.utils.extensions.isPasswordValid
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.docto.data.City
import com.toybeth.docto.data.Country
import com.toybeth.docto.data.Property
import com.toybeth.docto.data.State
import com.toybeth.docto.data.registration.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PatientRegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : BaseViewModel(){

    val countryList = MutableLiveData<List<Country>>()
    val stateList = MutableLiveData<List<State>>()
    val cityList = MutableLiveData<List<City>>()

    // ... First Screen
    val imageUri = mutableStateOf<Uri?>(null)
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
    val identityVerificationType = Property<String>()
    val identityVerificationNumber = Property<String>()
    val socialSecurityNumber = Property<String>()
    val address = Property<String>()
    val stateName = Property<String>()
    val cityName = Property<String>()
    val zipCode = Property<String>()

    // Third screen
    private val insuranceType = Property<String>()
    val referringDoctorAddress = Property<String>()
    val referringDoctorName = Property<String>()
    val referringDoctorPhoneNumber = Property<String>()
    val insuranceName = Property<String>()
    val insuranceNumber = Property<String>()
    val insurancePolicyHolderName = Property<String>()
    val showInsuranceDetailsForm = mutableStateOf(false)
    var insuranceTypes = listOf<String>()

    private val selectedCountry = Property<Country?>()
    private val selectedState = mutableStateOf<State?>(null)
    private val selectedCity = mutableStateOf<City?>(null)

    val moveNext = SingleLiveEvent<Boolean>()

    init {
        loadCountryStateAndCities()
        getInsuranceTypes()
    }

    fun setDateOfBirth(timeInMillis: Long) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        dateOfBirth.state.value = formatter.format(Date(timeInMillis))
    }

    fun setCountry(country: Country) {
        selectedCountry.state.value = country
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

    fun setInsuranceType(insuranceType: String) {
        this.insuranceType.state.value = insuranceType
        this.insuranceType.error.value = null
        showInsuranceDetailsForm.value = insuranceTypes.indexOf(insuranceType) == 1
    }

    fun getInsuranceType(): Property<String> {
        return this.insuranceType
    }

    fun getSelectedCountryCode(): String {
        return if (selectedCountry.state.value != null) {
            selectedCountry.state.value!!.phone
        } else {
            ""
        }
    }

    fun verifyFirstPage(): Boolean {
        var isVerified = true
        if(firstName.state.value.isNullOrEmpty()) {
            isVerified = false
            firstName.error.value = "This field is required"
        }
        if(lastName.state.value.isNullOrEmpty()) {
            isVerified = false
            lastName.error.value = "This field is required"
        }
        if(selectedCountry.state.value == null) {
            isVerified = false
            country.error.value = "Select your country"
        }
        if(mobileNumber.state.value.isNullOrEmpty()) {
            isVerified = false
            mobileNumber.error.value = "This field is required"
        }
        if(!email.state.value.isEmailValid()) {
            isVerified = false
            email.error.value = "This field is required"
        }
        if (!password.state.value.isPasswordValid()) {
            isVerified = false
            password.error.value = "This field is required"
        }
        if (!confirmPassword.state.value.isPasswordValid()) {
            isVerified = false
            confirmPassword.error.value = "This field is required"
        } else if (
            password.state.value != confirmPassword.state.value
        ) {
            isVerified = false
            confirmPassword.error.value = "Passwords do not match"
        }
        if (dateOfBirth.state.value.isNullOrEmpty()) {
            isVerified = false
            dateOfBirth.error.value = "This field is required"
        }

        return isVerified
    }

    fun verifySecondPage(): Boolean {
        var isValid = true
        if(identityVerificationType.state.value.isNullOrEmpty()) {
            isValid = false
            identityVerificationType.error.value = "This field is required"
        }
        if(identityVerificationNumber.state.value.isNullOrEmpty()) {
            isValid = false
            identityVerificationNumber.error.value = "This field is required"
        }
        if(socialSecurityNumber.state.value.isNullOrEmpty()) {
            isValid = false
            socialSecurityNumber.error.value = "This field is required"
        }
        if(address.state.value.isNullOrEmpty()) {
            isValid = false
            address.error.value = "This field is required"
        }
        if(stateName.state.value.isNullOrEmpty()) {
            isValid = false
            stateName.error.value = "This field is required"
        }
        if(!cityList.value.isNullOrEmpty() && cityName.state.value.isNullOrEmpty()) {
            isValid = false
            cityName.error.value = "This field is required"
        }
        if(zipCode.state.value.isNullOrEmpty()) {
            isValid = false
            zipCode.error.value = "This field is required"
        }
        return isValid
    }

    fun verifyThirdPage(): Boolean {
        var isValid = true
        if(insuranceType.state.value.isNullOrEmpty()) {
            isValid = false
            insuranceType.error.value = "This field is required"
        }
        if(showInsuranceDetailsForm.value) {
            if (insuranceName.state.value.isNullOrEmpty()) {
                isValid = false
                insuranceName.error.value = "This field is required"
            }
            if(insuranceNumber.state.value.isNullOrEmpty()) {
                isValid = false
                insuranceNumber.error.value = "This field is required"
            }
            if(insurancePolicyHolderName.state.value.isNullOrEmpty()) {
                isValid = false
                insurancePolicyHolderName.error.value = "This field is required"
            }
        }
        return isValid
    }

    fun moveNext() {
        moveNext.postValue(true)
    }

    fun registerPatient() {
        viewModelScope.launchIOWithExceptionHandler({
            repository.registerPatient(
                firstName.state.value!! + " " + lastName.state.value!!,
                selectedCountry.state.value!!.phone,
                mobileNumber.state.value!!,
                email.state.value!!,
                password.state.value!!,
                gender.state.value!!,
                dateOfBirth.state.value!!,
                imageUri.value!!,
                identityVerificationType.state.value!!,
                identityVerificationNumber.state.value!!,
                imageUri.value!!,
                address.state.value!!,
                socialSecurityNumber.state.value!!,
                stateName.state.value!!,
                cityName.state.value!!,
                zipCode.state.value!!,
                referringDoctorAddress.state.value,
                referringDoctorName.state.value,
                referringDoctorPhoneNumber.state.value,
                insuranceType.state.value!!,
                insuranceName.state.value,
                insuranceNumber.state.value,
                insurancePolicyHolderName.state.value
            )
        }, {
            it.printStackTrace()
        })
    }

    private fun getInsuranceTypes() {
        this.insuranceTypes = listOf("Self paid", "Insurance verified")
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