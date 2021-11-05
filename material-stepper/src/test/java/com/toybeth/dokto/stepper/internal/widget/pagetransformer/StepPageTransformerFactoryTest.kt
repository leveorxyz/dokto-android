package com.toybeth.dokto.stepper.internal.widget.pagetransformer

import android.content.res.Resources
import android.os.Build
import androidx.viewpager2.widget.ViewPager2
import com.toybeth.dokto.stepper.R
import test.runner.StepperRobolectricTestRunner
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class StepPageTransformerFactoryTest {

    @Test
    @Config(
        sdk = [Build.VERSION_CODES.JELLY_BEAN_MR1],
        qualifiers = StepperRobolectricTestRunner.QUALIFIER_LDRTL
    )
    fun `Should create StepperRtlPageTransformer on JellyBean_MR1 when RTL locale selected`() {
        //when
        val resources: Resources = mock()
        whenever(resources.getBoolean(R.bool.ms_rtlEnabled)).thenReturn(true)

        val pageTransformer = StepPageTransformerFactory.createPageTransformer(resources)

        //then
        assertNotNull("PageTransformer cannot be null", pageTransformer)
        assertThat(pageTransformer, CoreMatchers.instanceOf(StepperRtlPageTransformer::class.java))
    }

    @Test
    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN_MR1))
    fun `Should return 'null' by default on JellyBean_MR1`() {
        //when
        val pageTransformer =
            StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application.resources)

        //then
        assertPageTransformerIsNull(pageTransformer)
    }

    @Test
    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN))
    fun `Should return 'null' by default on JellyBean`() {
        //when
        val pageTransformer =
            StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application.resources)

        //then
        assertPageTransformerIsNull(pageTransformer)
    }

    /**
     * Layout direction qualifiers were added in JB MR1 so below this OS version RTL should be ignored.
     */
    @Test
    @Config(
        sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN),
        qualifiers = StepperRobolectricTestRunner.QUALIFIER_LDRTL
    )
    fun `Should return 'null' on JellyBean when RTL locale selected`() {
        //when
        val pageTransformer =
            StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application.resources)

        //then
        assertPageTransformerIsNull(pageTransformer)
    }

    fun assertPageTransformerIsNull(pageTransformer: ViewPager2.PageTransformer?) {
        assertNull("PageTransformer must be null", pageTransformer)
    }

}