package com.toybeth.dokto.stepper.internal.feedback

import com.toybeth.dokto.stepper.R
import com.toybeth.dokto.stepper.StepperLayout
import com.toybeth.dokto.stepper.internal.util.AnimationUtil
import test.TYPE_TABS
import test.assertion.StepperLayoutAssert
import test.createStepperLayoutInActivity
import test.runner.StepperRobolectricTestRunner
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class ContentFadeStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..loading."
        val CONTENT_FADE_ALPHA = 0.1f
    }

    lateinit var stepperLayout: StepperLayout

    lateinit var feedbackType: ContentFadeStepperFeedbackType

    @Before
    fun setUp() {
        val attributeSet = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, TYPE_TABS)
                .addAttribute(R.attr.ms_stepperFeedback_contentFadeAlpha, CONTENT_FADE_ALPHA.toString())
                .build()
        stepperLayout = createStepperLayoutInActivity(attributeSet)
        feedbackType = ContentFadeStepperFeedbackType(stepperLayout)
    }

    @Ignore
    @Test
    fun `Should fade content out when showing progress`() {
        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        assertStepperLayout()
                .pagerHasAlpha(CONTENT_FADE_ALPHA)
    }

    @Test
    fun `Should fade content in when hiding progress`() {
        //when
        feedbackType.hideProgress()

        //then
        assertStepperLayout()
                .pagerHasAlpha(AnimationUtil.ALPHA_OPAQUE)
    }

    fun assertStepperLayout(): StepperLayoutAssert {
        return StepperLayoutAssert.assertThat(stepperLayout)
    }

}