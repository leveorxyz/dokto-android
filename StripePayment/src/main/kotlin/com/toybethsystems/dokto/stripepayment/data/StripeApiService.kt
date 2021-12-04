package com.toybethsystems.dokto.stripepayment.data

import com.toybethsystems.dokto.stripepayment.data.model.EphemeralKey
import com.toybethsystems.dokto.stripepayment.data.model.PaymentIntent
import com.toybethsystems.dokto.stripepayment.data.model.StripeCustomer
import retrofit2.Response
import retrofit2.http.*

interface StripeApiService {

    @POST("v1/customers")
    suspend fun createCustomer() : Response<StripeCustomer>

    @POST("v1/ephemeral_keys")
    @FormUrlEncoded
    suspend fun createEphemeralKey(
        @Field("customer") customerId: String,
        @Header("Stripe-Version") stripeVersion: String = "2020-08-27"
    ): Response<EphemeralKey>

    @POST("v1/payment_intents")
    @FormUrlEncoded
    suspend fun getPaymentIntent(
        @Field("customer") customerId: String,
        @Field("currency") currency: String,
        @Field("amount") amount: Int,
        @Header("Stripe-Version") stripeVersion: String = "2020-08-27"
    ): Response<PaymentIntent>
}