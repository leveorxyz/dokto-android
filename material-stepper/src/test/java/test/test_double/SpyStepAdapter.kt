package test.test_double

import android.content.Context
import androidx.fragment.app.FragmentManager
import android.util.SparseArray
import androidx.lifecycle.Lifecycle
import org.mockito.kotlin.spy
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.adapter.AbstractFragmentStepAdapter
import com.toybeth.dokto.stepper.viewmodel.StepViewModel

/**
 * Creates Spy [DummyStepFragment]s which can be later verified.
 */
class SpyStepAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    context: Context,
    val viewModels: List<StepViewModel>
) : AbstractFragmentStepAdapter(fm, lifecycle, context) {

    val steps = SparseArray<Step>()

    override fun getViewModel(position: Int): StepViewModel {
        return viewModels[position]
    }

    override fun createStep(position: Int): Step {
        val stepFragment = spy(DummyStepFragment())
        steps.put(position, stepFragment)
        return stepFragment
    }

    override fun getCount() = viewModels.size
}