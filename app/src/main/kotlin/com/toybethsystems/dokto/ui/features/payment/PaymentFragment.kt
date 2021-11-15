package com.toybethsystems.dokto.ui.features.payment

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<PaymentViewModel>() {

    override val viewModel: PaymentViewModel by viewModels()

    override val composeView: ComposeView
        get() {
            return ComposeView(requireContext()).apply {
                setContent {
                    PaymentScreen()
                }
            }
        }
}