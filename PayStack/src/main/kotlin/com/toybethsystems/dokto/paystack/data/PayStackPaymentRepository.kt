package com.toybethsystems.dokto.paystack.data

import com.toybethsystems.dokto.paystack.data.model.request.InitializeTransactionRequest
import com.toybethsystems.dokto.paystack.data.model.response.BasePayStackApiResponse
import com.toybethsystems.dokto.paystack.data.model.response.PayStackPayment
import javax.inject.Inject

class PayStackPaymentRepository @Inject constructor(
    private val payStackApiService: PayStackApiService
) {

    suspend fun initializeTransaction(email: String, amount: String): PayStackPayment {
        val body = InitializeTransactionRequest(email, amount)
        val response = payStackApiService.initializeTransaction(body)
        if(response.body() != null && response.isSuccessful) {
            return response.body()!!.data
        } else {
            throw Throwable(response.errorBody()?.toString())
        }
    }

    suspend fun validateTransaction(transactionReference: String): BasePayStackApiResponse {
        val response = payStackApiService.validateTransaction(transactionReference)
        if(response.body() != null && response.isSuccessful) {
            return response.body()!!
        } else {
            throw Throwable(response.errorBody()?.toString())
        }
    }
}