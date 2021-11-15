package com.toybethsystems.dokto.ui.features.registration.clinic

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.Fragment
import com.toybethsystems.dokto.ui.features.registration.clinic.first.ClinicRegistrationFirstStepFragment
import com.toybethsystems.dokto.ui.features.registration.doctor.form.first.DoctorRegistrationFirstStepFragment
import com.toybethsystems.dokto.ui.features.registration.doctor.form.fourth.DoctorRegistrationFourthStepFragment
import com.toybethsystems.dokto.ui.features.registration.doctor.form.second.DoctorRegistrationSecondStepFragment
import com.toybethsystems.dokto.ui.features.registration.doctor.form.third.DoctorRegistrationThirdStepFragment
import com.toybethsystems.dokto.ui.features.registration.patient.first.PatientRegistrationFirstStepFragment
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.adapter.AbstractFragmentStepAdapter
import com.toybeth.dokto.stepper.viewmodel.StepViewModel

@ExperimentalMaterialApi
@ExperimentalUnitApi
class ClinicRegistrationFormStepsAdapter(private val titles: List<String>, fragment: Fragment) : AbstractFragmentStepAdapter(
    fragment.childFragmentManager,
    fragment.lifecycle,
    fragment.requireContext()
) {

    override fun getViewModel(position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context)
            .setTitle(titles[position])
        if (position == 1) {
            builder.setSubtitle("Optional")
        }
        return builder
            .create()
    }

    override fun createStep(position: Int): Step {
        return when(position) {
            0 -> ClinicRegistrationFirstStepFragment()
            else -> ClinicRegistrationFirstStepFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }
}