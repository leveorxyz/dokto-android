package com.toybeth.dokto.stripepayment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.toybeth.dokto.stripepayment.BuildConfig
import com.toybeth.dokto.stripepayment.databinding.DialogStripePaymentBinding
import com.toybeth.dokto.stripepayment.ui.model.StripePaymentRelatedInitialData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StripePaymentFragment : DialogFragment() {

    private var paymentSheet: PaymentSheet? = null

    private val viewModel: StripePaymentViewModel by viewModels()
    private lateinit var binding: DialogStripePaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogStripePaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

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