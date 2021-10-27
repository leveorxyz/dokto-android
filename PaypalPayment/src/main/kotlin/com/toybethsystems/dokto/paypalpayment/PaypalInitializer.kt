package com.toybethsystems.dokto.paypalpayment

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.Logger
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
            returnUrl = "${context.packageName}://paypalpay",
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)
        Logger.d("PAYPAL INITIALIZED")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}