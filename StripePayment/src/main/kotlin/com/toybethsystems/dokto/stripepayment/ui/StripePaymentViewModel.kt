package com.toybethsystems.dokto.stripepayment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybethsystems.dokto.stripepayment.data.StripePaymentRepository
import com.toybethsystems.dokto.stripepayment.ui.model.StripePaymentRelatedInitialData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StripePaymentViewModel @Inject constructor(
    private val repository: StripePaymentRepository
) : BaseViewModel() {

    val isInitialDataFetchedLiveData = MutableLiveData<Boolean?>(null)
    val initialDataLiveData = MutableLiveData<StripePaymentRelatedInitialData>()

    fun fetchInitialData(amount: Int, currency: String) {
        viewModelScope.launchIOWithExceptionHandler({
            val initialData = repository.fetchInitData(amount, currency)
            initialDataLiveData.postValue(initialData)
            isInitialDataFetchedLiveData.postValue(true)
        }, {
            it.printStackTrace()
            message.postValue(it.message)
            isInitialDataFetchedLiveData.postValue(false)
        })
    }
}