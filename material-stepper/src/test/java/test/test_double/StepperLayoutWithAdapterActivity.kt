package test.test_double

import android.os.Bundle
import com.toybeth.dokto.stepper.StepperLayoutActivity
import com.toybeth.dokto.stepper.viewmodel.StepViewModel

/**
 * A [StepperLayoutActivity] which also sets an adapter in [onCreate].
 */
open class StepperLayoutWithAdapterActivity : StepperLayoutActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModels = listOf(getFirstStepViewModel(), getSecondStepViewModel(), getLastStepViewModel())

        val stepAdapter = SpyStepAdapter(supportFragmentManager, lifecycle, this, viewModels)
        stepperLayout.adapter = stepAdapter
    }

    open fun getFirstStepViewModel(): StepViewModel {
        return defaultStepViewModel()
    }

    open fun getSecondStepViewModel(): StepViewModel {
        return defaultStepViewModel()
    }

    open fun getLastStepViewModel(): StepViewModel {
        return defaultStepViewModel()
    }

    private fun defaultStepViewModel(): StepViewModel {
        return StepViewModel.Builder(this)
                .setTitle("Dummy title")
                .create()
    }
}