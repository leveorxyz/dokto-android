package com.toybeth.dokto.stepper.internal.feedback

import androidx.viewpager2.widget.ViewPager2
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import com.toybeth.dokto.stepper.R
import com.toybeth.dokto.stepper.StepperLayout
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class DisabledContentInteractionStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
    }

    val mockStepPager: ViewPager2 = mock()

    val mockStepperLayout: StepperLayout = mock {
        on { findViewById<ViewPager2>(R.id.ms_stepPager) } doReturn mockStepPager
    }

    val feedbackType: DisabledContentInteractionStepperFeedbackType = DisabledContentInteractionStepperFeedbackType(mockStepperLayout)

    @Test
    fun `Should block content interaction when showing progress`() {
        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        verify(mockStepPager).setUserInputEnabled(false)
    }

    @Test
    fun `Should enable content interaction when hiding progress`() {
        //when
        feedbackType.hideProgress()

        //then
        verify(mockStepPager).setUserInputEnabled(true)
    }
}