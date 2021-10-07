package com.toybeth.dokto.stripepayment.ui

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.dokto.stripepayment.BuildConfig
import com.toybeth.dokto.stripepayment.R
import com.toybeth.dokto.stripepayment.ui.model.StripePaymentRelatedInitialData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StripePaymentFragment: BaseFragment<StripePaymentViewModel>() {

    private var paymentSheet: PaymentSheet? = null

    override val viewModel: StripePaymentViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_stripe_payment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PaymentConfiguration.init(requireContext(), BuildConfig.STRIPE_PUBLISHABLE_KEY)

        paymentSheet = PaymentSheet(this) { result ->
            onPaymentSheetResult(result)
        }

        viewModel.initialDataLiveData.observe(viewLifecycleOwner) {
            presentPaymentSheet(it)
        }

        viewModel.fetchInitialData(500, "usd")
    }

    private fun presentPaymentSheet(stripePaymentRelatedInitialData: StripePaymentRelatedInitialData) {
        paymentSheet?.presentWithPaymentIntent(
            stripePaymentRelatedInitialData.paymentIntentSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Example, Inc.",
                customer = PaymentSheet.CustomerConfiguration(
                    id = stripePaymentRelatedInitialData.customerId,
                    ephemeralKeySecret = stripePaymentRelatedInitialData.ephemeralKeySecret
                )
            )
        )
    }

    private fun onPaymentSheetResult(result: PaymentSheetResult) {
        Logger.d(result)
        findNavController().popBackStack()
    }
}