package com.toybeth.dokto.paystack.data

import com.toybeth.dokto.paystack.data.model.response.BasePayStackApiResponse
import com.toybeth.dokto.paystack.data.model.response.PayStackBankListResponse
import com.toybeth.dokto.paystack.data.model.request.InitializeTransactionRequest
import com.toybeth.dokto.paystack.data.model.response.InitializeTransactionResponse
import retrofit2.Response
import retrofit2.http.*

interface PayStackApiService {

    @GET("transaction/verify/{transaction_reference}")
    suspend fun validateTransaction(
        @Path("transaction_reference") transactionReferenceNo: String
    ): Response<BasePayStackApiResponse>

    @POST("transaction/initialize")
    suspend fun initializeTransaction(
        @Body requestBody: InitializeTransactionRequest
    ): Response<InitializeTransactionResponse>

    @GET("bank")
    suspend fun getBankList(@Query("pay_with_bank") payWithBank: Boolean = true): Response<PayStackBankListResponse>
}