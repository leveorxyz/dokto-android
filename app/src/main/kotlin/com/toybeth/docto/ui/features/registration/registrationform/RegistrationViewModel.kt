package com.toybeth.docto.ui.features.registration.registrationform

import androidx.compose.runtime.mutableStateListOf
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.data.Country
import com.toybeth.docto.data.registration.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: RegistrationRepository) :
    BaseViewModel() {

    val countryList = mutableStateListOf<Country>()

    init {
        loadCountryStateAndCities()
    }

    private fun loadCountryStateAndCities() {
        countryList.addAll(repository.getCountryStateCityList())
    }
}