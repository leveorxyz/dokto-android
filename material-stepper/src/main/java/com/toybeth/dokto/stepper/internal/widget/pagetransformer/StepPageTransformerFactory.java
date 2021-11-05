package com.toybeth.dokto.stepper.internal.widget.pagetransformer;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.toybeth.dokto.stepper.R;

/**
 * Creates a page transformer to be used by {@link com.toybeth.dokto.stepper.internal.widget.StepViewPager}.
 *
 * @author Piotr Zawadzki
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class StepPageTransformerFactory {

    private StepPageTransformerFactory() {
    }

    /**
     * Creates a {@link ViewPager.PageTransformer}.
     * If layout direction is in RTL it returns {@link StepperRtlPageTransformer}, <i>null</i> otherwise.
     *
     * @param Resources resources
     * @return page transformer
     */
    @Nullable
    public static ViewPager2.PageTransformer createPageTransformer(@NonNull Resources resources) {
        boolean rtlEnabled = resources.getBoolean(R.bool.ms_rtlEnabled);
        return rtlEnabled ? new StepperRtlPageTransformer() : null;
    }

}
