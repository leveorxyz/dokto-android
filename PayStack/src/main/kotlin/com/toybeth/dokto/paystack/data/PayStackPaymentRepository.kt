package com.toybeth.dokto.paystack.data

import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import retrofit2.Response
import javax.inject.Inject

class PayStackPaymentRepository @Inject constructor(
    private val payStackApiService: PayStackApiService
) {

    suspend fun validateTransaction(transactionReference: String): ValidatePayStackTransactionResponse {
        val response = payStackApiService.validateTransaction(transactionReference)
        if(response.body() != null && response.isSuccessful) {
            return response.body()!!
        } else {
            throw Throwable(response.errorBody()?.toString())
        }
    }
}