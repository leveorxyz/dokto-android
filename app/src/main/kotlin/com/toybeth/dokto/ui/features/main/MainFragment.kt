package com.toybeth.dokto.ui.features.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybeth.dokto.R
import com.toybeth.dokto.base.ui.BaseFragment
import com.toybeth.dokto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                VideoCallStartButton()
            }
        }

    @Composable
    private fun VideoCallStartButton() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navigateToVideoCall()
                }
            ) {

            }
        }
    }

    private fun navigateToVideoCall() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToTwilioCallFragment()
        )
    }
}