package com.toybeth.dokto.paystack.ui

import androidx.lifecycle.viewModelScope
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.SingleLiveEvent
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.dokto.paystack.data.PayStackPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PayStackPaymentViewModel @Inject constructor(
    private val repository: PayStackPaymentRepository
) : BaseViewModel() {

    val isCardNumberWrong = SingleLiveEvent<Boolean>()
    val isExpireDateWrong = SingleLiveEvent<Boolean>()
    val isCcvWrong = SingleLiveEvent<Boolean>()
    val validCard = SingleLiveEvent<Card>()
    val paymentUrl = SingleLiveEvent<String>()

    fun validateCard(cardNumber: String, ccv: String, expireDate: String) {
        val expireMonth = getExpireMonthFromExpireDate(expireDate)
        val expireYear = getExpireYearFromExpireDate(expireDate)
        val card = Card(cardNumber, expireMonth, expireYear, ccv)
        if(!card.validNumber()) {
            isCardNumberWrong.postValue(true)
        } else if(!card.validExpiryDate()) {
            isExpireDateWrong.postValue(true)
        } else if(!card.validCVC()) {
            isCcvWrong.postValue(true)
        } else {
            validCard.postValue(card)
        }
    }

    fun initializeTransaction(email: String, amount: String) {
        viewModelScope.launchIOWithExceptionHandler({
            val transaction = repository.initializeTransaction(email, amount)
            paymentUrl.postValue(transaction.paymentUrl)
        }, {
            it.printStackTrace()
        })
    }

    private fun getExpireMonthFromExpireDate(expireDate: String): Int {
        return try {
            expireDate.split("/").first().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private fun getExpireYearFromExpireDate(expireDate: String): Int {
        return try {
            expireDate.split("/")[1].toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun validateTransaction(transaction: Transaction?) {
        if(transaction?.reference != null) {
            viewModelScope.launchIOWithExceptionHandler({
                repository.validateTransaction(transaction.reference)
            }, {
                it.printStackTrace()
            })
        } else {
            Logger.d("SOMETHING WENT WRONG")
        }
    }

}