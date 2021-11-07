package com.toybeth.docto.ui.features.registration.registrationform

import android.os.Bundle
import android.view.View
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@ExperimentalUnitApi
@AndroidEntryPoint
@ExperimentalMaterialApi
class RegistrationFirstStepFragment : BaseFragment<RegistrationViewModel>(), Step {

    private var datePicker: MaterialDatePicker<Long>? = null

    override val viewModel: RegistrationViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                DoctorRegistrationSecondScreen(
                    viewModel
                )
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

    private fun showDatePicker(onDatePicked: (day: Int, month: Int, year: Int) -> Unit) {
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
            val calendar = Calendar.getInstance().apply {
                timeInMillis = it
            }
            onDatePicked.invoke(
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR),
            )
        }
        datePicker?.show(childFragmentManager.beginTransaction(), "date picker")
    }
}