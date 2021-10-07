package com.toybeth.dokto.stripepayment.ui

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.toybeth.docto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StripePaymentFragment: BaseFragment<StripePaymentViewModel>() {

    override val viewModel: StripePaymentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PaymentConfiguration.init(requireContext(), STRIPE_PUBLISHABLE_KEY)

        paymentSheet = PaymentSheet(this) { result ->
            onPaymentSheetResult(result)
        }
    }
}

private fun presentPaymentSheet() {
    paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
            merchantDisplayName = "Example, Inc.",
            customer = PaymentSheet.CustomerConfiguration(
                id = customerId,
                ephemeralKeySecret = ephemeralKeySecret
            )
        )
    )
}