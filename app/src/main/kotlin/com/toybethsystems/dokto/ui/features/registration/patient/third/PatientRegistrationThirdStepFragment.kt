package com.toybethsystems.dokto.ui.features.registration.patient.third

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import com.toybethsystems.dokto.ui.features.registration.patient.PatientRegistrationViewModel
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientRegistrationThirdStepFragment : BaseFragment<PatientRegistrationViewModel>(), Step {

    override val viewModel: PatientRegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                PatientRegistrationThirdScreen(viewModel)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun verifyStep(): VerificationError? {
        return if(viewModel.verifyThirdPage()) {
            null
        } else {
            VerificationError("Fill-up all fields")
        }
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {
//        TODO("Not yet implemented")
    }
}