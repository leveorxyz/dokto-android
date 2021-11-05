package com.toybeth.dokto.stepper.internal.feedback

import android.view.View
import com.toybeth.dokto.stepper.R
import com.toybeth.dokto.stepper.StepperLayout
import test.TYPE_TABS
import test.assertion.StepperLayoutAssert
import test.createAttributeSetWithStepperType
import test.createStepperLayoutInActivity
import test.runner.StepperRobolectricTestRunner
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class ContentOverlayStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
        val CUSTOM_BACKGROUND_RESOURCE_ID = R.drawable.ms_ic_check
    }

    private val mockOverlayView: View = mock()

    lateinit var stepperLayout: StepperLayout

    lateinit var feedbackType: ContentOverlayStepperFeedbackType

    @Before
    fun setUp() {
        stepperLayout = createStepperLayoutInActivity(createAttributeSetWithStepperType(TYPE_TABS))
    }

    @Test
    fun `Overlay should have visibility = GONE by default when 'content_overlay' is not set`() {
        val overlay = stepperLayout.findViewById<View>(R.id.ms_stepPagerOverlay)
        assertNotNull(overlay)
        assertTrue(overlay.isGone)
    }

    @Test
    fun `Should use default overlay background when a custom background was not set`() {
        //given
        val spyStepperLayout = spy(stepperLayout)
        doReturn(mockOverlayView).whenever(spyStepperLayout)
            .findViewById<View>(R.id.ms_stepPagerOverlay)

        //when
        feedbackType = ContentOverlayStepperFeedbackType(spyStepperLayout)

        //then
        verify(mockOverlayView, never()).setBackgroundResource(any())
    }

    @Test
    fun `Should use custom overlay background when a custom background was set`() {
        //given
        val spyStepperLayout = spy(stepperLayout)
        doReturn(mockOverlayView).whenever(spyStepperLayout)
            .findViewById<View>(R.id.ms_stepPagerOverlay)
        doReturn(CUSTOM_BACKGROUND_RESOURCE_ID).whenever(spyStepperLayout).contentOverlayBackground

        //when
        feedbackType = ContentOverlayStepperFeedbackType(spyStepperLayout)

        //then
        verify(mockOverlayView).setBackgroundResource(CUSTOM_BACKGROUND_RESOURCE_ID)
    }

    @Test
    fun `Should show overlay with alpha set to 0 once feedback type is created`() {
        //when
        createFeedbackType()

        //then
        assertStepperLayout()
            .hasContentOverlayHidden()
    }

    @Ignore
    @Test
    fun `Should fade overlay in when showing progress`() {
        //given
        createFeedbackType()

        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        assertStepperLayout()
            .hasContentOverlayShown()
    }

    @Test
    fun `Should fade overlay out when hiding progress`() {
        //given
        createFeedbackType()
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //when
        feedbackType.hideProgress()

        //then
        assertStepperLayout()
            .hasContentOverlayHidden()
    }

    private fun createFeedbackType() {
        feedbackType = ContentOverlayStepperFeedbackType(stepperLayout)
    }

    fun assertStepperLayout(): StepperLayoutAssert {
        return StepperLayoutAssert.assertThat(stepperLayout)
    }

}