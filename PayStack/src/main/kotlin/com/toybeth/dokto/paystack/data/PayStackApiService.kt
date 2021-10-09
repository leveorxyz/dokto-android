package com.toybeth.dokto.paystack.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PayStackApiService {

    @GET("transaction/verify/{transaction_reference}")
    suspend fun validateTransaction(
        @Path("transaction_reference") transactionReferenceNo: String
    ): Response<ValidatePayStackTransactionResponse>
}