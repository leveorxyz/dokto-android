package com.toybethsystems.dokto.paypalpayment

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction

class PaypalInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val config = CheckoutConfig(
            application = context as Application,
            clientId = BuildConfig.PAYPAL_CLIENT_ID,
            environment = Environment.SANDBOX,
            returnUrl = "com.toybethsystems.dokto://paypalpay",
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}