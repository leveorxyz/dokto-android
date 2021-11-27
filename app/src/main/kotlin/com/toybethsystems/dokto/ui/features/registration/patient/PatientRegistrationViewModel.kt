package com.toybethsystems.dokto.ui.features.registration.patient

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.SingleLiveEvent
import com.toybethsystems.dokto.base.utils.extensions.isEmailValid
import com.toybethsystems.dokto.base.utils.extensions.isPasswordValid
import com.toybethsystems.dokto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybethsystems.dokto.data.City
import com.toybethsystems.dokto.data.Country
import com.toybethsystems.dokto.data.Property
import com.toybethsystems.dokto.data.State
import com.toybethsystems.dokto.data.registration.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PatientRegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : BaseViewModel() {

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

    // ... Second Screen
    val identityVerificationType = Property<String>()
    val identityVerificationNumber = Property<String>()
    val socialSecurityNumber = Property<String>()
    val identityVerificationImageUri = Property<Uri>()
    val address = Property<String>()
    val stateName = Property<String>()
    val cityName = Property<String>()
    val zipCode = Property<String>()

    // ... Third screen
    private val insuranceType = Property<String>()
    val referringDoctorAddress = Property<String>()
    val referringDoctorName = Property<String>()
    val referringDoctorPhoneNumber = Property<String>()
    val insuranceName = Property<String>()
    val insuranceNumber = Property<String>()
    val insurancePolicyHolderName = Property<String>()
    val showInsuranceDetailsForm = mutableStateOf(false)
    var insuranceTypes = listOf<String>()

    val selectedCountry = Property<Country?>()

    val moveNext = SingleLiveEvent<Boolean>()
    val registrationSuccess = SingleLiveEvent<Boolean>()

    init {
        loadCountryList()
        getInsuranceTypes()
    }

    fun setDateOfBirth(timeInMillis: Long) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        dateOfBirth.state.value = formatter.format(Date(timeInMillis))
    }

    fun setCountry(country: Country) {
        selectedCountry.state.value = country
        getStateList(country.code)
    }

    fun setState(state: State) {
        stateName.state.value = state.name
        country.state.value?.let {
            getCityList(it.code, state.code)
        }
    }

    fun setCity(city: City) {
        cityName.state.value = city.name
    }

    fun setInsuranceType(insuranceType: String) {
        this.insuranceType.state.value = insuranceType
        this.insuranceType.error.value = null
        showInsuranceDetailsForm.value = insuranceTypes.indexOf(insuranceType) == 1
    }

    fun getInsuranceType(): Property<String> {
        return this.insuranceType
    }

    fun getSelectedCountryPhoneCode(): String {
        return if (selectedCountry.state.value != null) {
            val phoneCode = selectedCountry.state.value!!.phone
            return if (phoneCode.startsWith("+")) {
                phoneCode
            } else {
                "+$phoneCode"
            }
        } else {
            ""
        }
    }

    fun verifyFirstPage(): Boolean {
        var isVerified = true

        if (profileImage.state.value == null) {
            isVerified = false
            profileImage.error.value = "Select a profile photo"
        }

        if (firstName.state.value.isNullOrEmpty()) {
            isVerified = false
            firstName.error.value = "This field is required"
        }

        if (lastName.state.value.isNullOrEmpty()) {
            isVerified = false
            lastName.error.value = "This field is required"
        }

        if (selectedCountry.state.value == null) {
            isVerified = false
            country.error.value = "Select your country"
        }

        if (mobileNumber.state.value.isNullOrEmpty()) {
            isVerified = false
            mobileNumber.error.value = "This field is required"
        }

        if (email.state.value.isNullOrEmpty()) {
            isVerified = false
            email.error.value = "This field is required"
        } else if (!email.state.value.isEmailValid()) {
            isVerified = false
            email.error.value = "Enter a valid email"
        }

        if (password.state.value.isNullOrEmpty()) {
            isVerified = false
            password.error.value = "This field is required"
        } else if (!password.state.value.isPasswordValid()) {
            isVerified = false
            password.error.value =
                "Password must be 8 characters long and must have minimum 1 number and 1 letter"
        }

        if (confirmPassword.state.value.isNullOrEmpty()) {
            isVerified = false
            confirmPassword.error.value = "This field is required"
        } else if (password.state.value != confirmPassword.state.value) {
            isVerified = false
            confirmPassword.error.value = "Passwords do not match"
        }

        if (gender.state.value.isNullOrEmpty()) {
            gender.error.value = "This field is required"
            isVerified = false
        }

        if (dateOfBirth.state.value.isNullOrEmpty()) {
            isVerified = false
            dateOfBirth.error.value = "This field is required"
        }

        return isVerified
    }

    fun verifySecondPage(): Boolean {
        var isValid = true

        if (identityVerificationType.state.value.isNullOrEmpty()) {
            isValid = false
            identityVerificationType.error.value = "This field is required"
        }

        if (identityVerificationNumber.state.value.isNullOrEmpty()) {
            isValid = false
            identityVerificationNumber.error.value = "This field is required"
        }

        if (identityVerificationImageUri.state.value == null) {
            isValid = false
            identityVerificationImageUri.error.value = "Select identity verification image"
        }

        if (address.state.value.isNullOrEmpty()) {
            isValid = false
            address.error.value = "This field is required"
        }

        if (!stateList.value.isNullOrEmpty() && stateName.state.value.isNullOrEmpty()) {
            isValid = false
            stateName.error.value = "This field is required"
        }

        if (!cityList.value.isNullOrEmpty() && cityName.state.value.isNullOrEmpty()) {
            isValid = false
            cityName.error.value = "This field is required"
        }

        if (zipCode.state.value.isNullOrEmpty()) {
            isValid = false
            zipCode.error.value = "This field is required"
        }

        return isValid
    }

    fun verifyThirdPage(): Boolean {
        var isValid = true

        if (insuranceType.state.value.isNullOrEmpty()) {
            isValid = false
            insuranceType.error.value = "This field is required"
        }

        if (showInsuranceDetailsForm.value) {
            if (insuranceName.state.value.isNullOrEmpty()) {
                isValid = false
                insuranceName.error.value = "This field is required"
            }
            if (insuranceNumber.state.value.isNullOrEmpty()) {
                isValid = false
                insuranceNumber.error.value = "This field is required"
            }
            if (insurancePolicyHolderName.state.value.isNullOrEmpty()) {
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
        loader.postValue(true)
        viewModelScope.launchIOWithExceptionHandler({
            val result = repository.registerPatient(
                fullName = firstName.state.value!! + " " + lastName.state.value!!,
                phoneCode = selectedCountry.state.value!!.phone,
                contactNo = mobileNumber.state.value!!,
                email = email.state.value!!,
                password = password.state.value!!,
                gender = gender.state.value!!,
                dateOfBirth = dateOfBirth.state.value!!,
                profilePhotoUri = imageUri.value!!,
                identificationType = identityVerificationType.state.value!!,
                identificationNumber = identityVerificationNumber.state.value!!,
                identificationPhotoUri = identityVerificationImageUri.state.value!!,
                street = address.state.value!!,
                socialSecurityNumber = socialSecurityNumber.state.value,
                state = stateName.state.value,
                city = cityName.state.value,
                zipCode = zipCode.state.value!!,
                referringDoctorAddress = referringDoctorAddress.state.value,
                referringDoctorFullName = referringDoctorName.state.value,
                referringDoctorPhoneNumber = referringDoctorPhoneNumber.state.value,
                insuranceType = insuranceType.state.value!!,
                insuranceName = insuranceName.state.value,
                insuranceNumber = insuranceNumber.state.value,
                insurancePolicyHolderName = insurancePolicyHolderName.state.value
            )
            registrationSuccess.postValue(result)
            loader.postValue(false)
        }, {
            loader.postValue(false)
            it.printStackTrace()
        })
    }

    private fun getInsuranceTypes() {
        this.insuranceTypes = listOf("Self paid", "Insurance verified")
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