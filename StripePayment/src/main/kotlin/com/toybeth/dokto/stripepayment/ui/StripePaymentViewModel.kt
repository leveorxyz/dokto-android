package com.toybeth.dokto.stripepayment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.dokto.stripepayment.data.StripePaymentRepository
import com.toybeth.dokto.stripepayment.ui.model.StripePaymentRelatedInitialData
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