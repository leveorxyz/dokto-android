package com.toybeth.docto.ui.features.registration.complete

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationCompleteFragment : BaseFragment<RegistrationCompleteViewModel>() {
    override val viewModel: RegistrationCompleteViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                RegistrationCompleteScreen(::navigateToLogin)
            }
        }

    private fun navigateToLogin() {
        findNavController().navigate(
            RegistrationCompleteFragmentDirections.actionRegistrationCompleteFragmentToLoginFragment()
        )
    }
}