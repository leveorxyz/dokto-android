package com.toybeth.dokto.stepper.internal.widget

import android.view.MotionEvent
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import org.mockito.kotlin.mock
import test.runner.StepperRobolectricTestRunner
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class StepViewPagerTest {

    private val mockTouchEvent: MotionEvent = mock()

    private val stepPager: ViewPager2 = ViewPager2(RuntimeEnvironment.application)

    @Test
    @Ignore("ViewPager2 isUserInputEnabled already covers this case")
    fun `Should steal motion events from children if blocking children is enabled`() {
        //given
        stepPager.isUserInputEnabled = false
        stepPager.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS

        //when
        val shouldStealMotionEventFromChildren = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertTrue(shouldStealMotionEventFromChildren)
    }

    @Test
    fun `Should not steal motion events from children by default`() {
        //when
        val shouldStealMotionEventFromChildren = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertFalse(shouldStealMotionEventFromChildren)
    }

    @Test
    fun `Should not handle events in 'onTouchEvent(_)' by default`() {
        //when
        val eventHandled = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertFalse(eventHandled)
    }

    @Test
    fun `Should handle touch events in 'onTouchEvent(_)' if blocking children is enabled`() {
        //when
        val eventHandled = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertFalse(eventHandled)
    }

}