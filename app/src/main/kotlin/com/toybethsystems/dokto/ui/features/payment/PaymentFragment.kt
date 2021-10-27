package com.toybethsystems.dokto.ui.features.payment

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybethsystems.dokto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<PaymentViewModel>() {

    override val viewModel: PaymentViewModel by viewModels()

    override val composeView: ComposeView
        get() {
            return ComposeView(requireContext()).apply {
                setContent {
                    PaymentScreen(
                        ::navigateToPaypalPayment,
                        ::navigateToStripePayment,
                        ::navigateToFlutterwavePayment,
                        ::navigateToPayStackPayment

                    )
                }
            }
        }

    private fun navigateToPaypalPayment() {
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToPaypalPaymentNavGraph()
          )
    }
          
    private fun navigateToStripePayment() {
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToStripePaymentNavGraph()
        )
    }

    private fun navigateToFlutterwavePayment() {
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToFlutterwavePaymentNavGraph()
        )
    }

    private fun navigateToPayStackPayment() {
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToPayStackPaymentDialog()
        )
    }
}