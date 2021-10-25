package com.toybeth.docto.ui.features.main

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybeth.docto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override val composeView: ComposeView?
        get() = ComposeView(requireContext()).apply {
            setContent {
                mainView(
                    this@MainFragment::navigateToPayment,
                    this@MainFragment::navigateToMap,
                    this@MainFragment::navigateToVideoCall
                )
            }
        }

    private fun navigateToPayment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToPaymentFragment()
        )
    }

    private fun navigateToMap() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMapsFragment()
        )
    }

    private fun navigateToVideoCall() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToVideoCallFragment()
        )
    }
}