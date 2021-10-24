package com.toybeth.dokto.stripepayment.data

import android.content.Context
import com.orhanobut.logger.Logger
import com.toybeth.dokto.stripepayment.R
import com.toybeth.dokto.stripepayment.data.model.EphemeralKey
import com.toybeth.dokto.stripepayment.data.model.PaymentIntent
import com.toybeth.dokto.stripepayment.data.model.StripeCustomer
import com.toybeth.dokto.stripepayment.ui.model.StripePaymentRelatedInitialData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StripePaymentRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stripeApiService: StripeApiService
) {
    private var customer: StripeCustomer? = null
    private var ephemeralKey: EphemeralKey? = null
    private var paymentIntent: PaymentIntent? = null

    suspend fun fetchInitData(amount: Int, currency: String): StripePaymentRelatedInitialData {
        try {
            val createCustomerResponse = stripeApiService.createCustomer()
            if (createCustomerResponse.isSuccessful && createCustomerResponse.body() != null) {
                customer = createCustomerResponse.body()
                val customerId: String = customer!!.id
                val ephemeralKeyResponse = stripeApiService.createEphemeralKey(customerId)
                val paymentIntentResponse =
                    stripeApiService.getPaymentIntent(customerId, currency, amount)
                if (ephemeralKeyResponse.isSuccessful && ephemeralKeyResponse.body() != null &&
                    paymentIntentResponse.isSuccessful && paymentIntentResponse.body() != null
                ) {
                    ephemeralKey = ephemeralKeyResponse.body()
                    paymentIntent = paymentIntentResponse.body()
                    return StripePaymentRelatedInitialData(
                        customerId,
                        ephemeralKey!!.secret,
                        paymentIntent!!.clientSecret
                    )
                } else {
                    Logger.d(ephemeralKeyResponse.errorBody())
                    Logger.d(paymentIntentResponse.errorBody()?.string())
                    throw Throwable(context.getString(R.string.stripe_initial_data_fetch_error))
                }
            } else {
                Logger.d(createCustomerResponse.errorBody())
                throw Throwable(context.getString(R.string.stripe_initial_data_fetch_error))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}