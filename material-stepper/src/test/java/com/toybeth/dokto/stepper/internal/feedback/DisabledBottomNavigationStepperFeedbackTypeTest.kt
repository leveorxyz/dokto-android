package com.toybeth.dokto.stepper.internal.feedback

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import com.toybeth.dokto.stepper.StepperLayout
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class DisabledBottomNavigationStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
    }

    val mockStepperLayout: StepperLayout = mock { }

    val feedbackType: DisabledBottomNavigationStepperFeedbackType = DisabledBottomNavigationStepperFeedbackType(mockStepperLayout)

    @Test
    fun `Should disable navigation buttons when showing progress`() {
        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        verify(mockStepperLayout).setNextButtonEnabled(false)
        verify(mockStepperLayout).setBackButtonEnabled(false)
        verify(mockStepperLayout).setCompleteButtonEnabled(false)
    }

    @Test
    fun `Should enable navigation buttons when hiding progress`() {
        //when
        feedbackType.hideProgress()

        //then
        verify(mockStepperLayout).setNextButtonEnabled(true)
        verify(mockStepperLayout).setBackButtonEnabled(true)
        verify(mockStepperLayout).setCompleteButtonEnabled(true)
    }

}