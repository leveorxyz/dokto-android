package com.toybeth.docto.ui.features.registration.patient.third

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.docto.ui.features.registration.patient.PatientRegistrationViewModel
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError

class PatientRegistrationThirdStepFragment : BaseFragment<PatientRegistrationViewModel>(), Step {

    override val viewModel: PatientRegistrationViewModel by viewModels()

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
        return null
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {
//        TODO("Not yet implemented")
    }
}