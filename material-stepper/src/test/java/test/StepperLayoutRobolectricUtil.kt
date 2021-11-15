package test

import android.util.AttributeSet
import com.toybeth.dokto.stepper.R
import com.toybeth.dokto.stepper.StepperLayout
import com.toybeth.dokto.stepper.StepperLayoutActivity
import test.test_double.StepperLayoutWithAdapterActivity
import com.toybeth.dokto.stepper.viewmodel.StepViewModel
import org.robolectric.Robolectric

/**
 * Creates common test operation for creating [StepperLayout] with Robolectric.
 *
 * @author Piotr Zawadzki
 */

val TYPE_PROGRESS_BAR = "progress_bar"
val TYPE_DOTS = "dots"
val TYPE_TABS = "tabs"
val TYPE_NONE = "none"

fun createAttributeSetWithStepperType(stepperType: String): AttributeSet {
    return Robolectric.buildAttributeSet()
        .addAttribute(R.attr.ms_stepperType, stepperType)
        .build()
}

fun createStepperLayoutInActivity(attributeSet: AttributeSet): StepperLayout {
    val controller = Robolectric.buildActivity(StepperLayoutActivity::class.java)
    controller.get().withStepperLayoutAttributes(attributeSet)
    return controller.setup().get().stepperLayout
}

fun createStepperLayoutWithAdapterSetInActivity(attributeSet: AttributeSet): StepperLayout {
    return createStepperLayoutWithAdapterSetInActivity(attributeSet, null, null, null)
}

/**
 * Creates a [StepperLayout] set in the Activity with [StepViewModel]s provided in the attributes.
 * If a [StepViewModel] is null then the default [StepViewModel] from [StepperLayoutWithAdapterActivity.defaultStepViewModel] is used.
 */
fun createStepperLayoutWithAdapterSetInActivity(
    attributeSet: AttributeSet,
    firstViewModel: StepViewModel?,
    middleViewModel: StepViewModel?,
    lastViewModel: StepViewModel?
): StepperLayout {

    val controller = Robolectric.buildActivity(StepperLayoutWithDefaultAdapterActivity::class.java)
    controller.get()
        .setViewModels(firstViewModel, middleViewModel, lastViewModel)
        .withStepperLayoutAttributes(attributeSet)

    return controller.setup().get().stepperLayout
}

class StepperLayoutWithDefaultAdapterActivity : StepperLayoutWithAdapterActivity() {
    private var firstViewModel: StepViewModel? = null
    private var middleViewModel: StepViewModel? = null
    private var lastViewModel: StepViewModel? = null

    override fun getFirstStepViewModel() = firstViewModel ?: super.getFirstStepViewModel()

    override fun getSecondStepViewModel() = middleViewModel ?: super.getSecondStepViewModel()

    override fun getLastStepViewModel() = lastViewModel ?: super.getLastStepViewModel()

    fun setViewModels(
        firstViewModel: StepViewModel?,
        middleViewModel: StepViewModel?,
        lastViewModel: StepViewModel?
    ): StepperLayoutWithDefaultAdapterActivity {
        this.firstViewModel = firstViewModel
        this.middleViewModel = middleViewModel
        this.lastViewModel = lastViewModel
        return this
    }

}
