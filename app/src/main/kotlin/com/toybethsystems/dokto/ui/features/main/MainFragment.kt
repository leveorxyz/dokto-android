package com.toybethsystems.dokto.ui.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                MainScreen(
                    ::navigateToPayment,
                    ::navigateToVideoCall
                )
            }
        }

    private fun navigateToVideoCall() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToTwilioCallFragment()
        )
    }

    private fun navigateToPayment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToPaymentFragment()
        )
    }

}