package com.toybethsystems.dokto.ui.features.registration.doctor.form.fourth

import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseFragment
import com.toybethsystems.dokto.base.utils.extensions.setContentView
import com.toybethsystems.dokto.ui.features.registration.doctor.form.RegistrationViewModel
import com.toybethsystems.dokto.base.theme.DoktoTheme
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError
import java.util.*

class DoctorRegistrationFourthStepFragment : BaseFragment<RegistrationViewModel>(), Step {

    private var datePicker: MaterialDatePicker<Long>? = null

    override val viewModel: RegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                DoktoTheme {
                    DoctorRegistrationFourthScreen(
                        viewModel,
                        this@DoctorRegistrationFourthStepFragment::showDatePicker
                    )
                }
            }
        }

    override fun verifyStep(): VerificationError? {
        return if(viewModel.verifyDoctorRegistrationFourthPage()) {
            null
        } else {
            VerificationError("Fill-up all fields")
        }
    }

    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    private fun showDatePicker(onDatePicked: (timeInMillis: Long) -> Unit) {
        try {
            datePicker?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.YEAR, 1950)
        val endDate = Calendar.getInstance()
        val constraints = CalendarConstraints.Builder()
            .setStart(startDate.timeInMillis)
            .setEnd(Calendar.getInstance().timeInMillis)
            .setValidator(DateValidatorPointBackward.before(endDate.timeInMillis))
            .build()
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date_of_birth))
            .setCalendarConstraints(constraints)
            .build()
        datePicker?.addOnPositiveButtonClickListener {
            onDatePicked.invoke(it)
        }
        datePicker?.show(childFragmentManager.beginTransaction(), "date picker")
    }
}