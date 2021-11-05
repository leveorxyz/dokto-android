package com.toybeth.docto.ui.features.registration.registrationform

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError

class RegistrationFirstStepFragment : BaseFragment<RegistrationViewModel>(), Step {

    override val viewModel: RegistrationViewModel by viewModels(ownerProducer = { requireParentFragment() })

    @ExperimentalUnitApi
    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                RegistrationFirstStepFormScreen()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {
//        TODO("Not yet implemented")
    }
}