/*
Copyright 2017 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.toybeth.dokto.stepper.internal.feedback;

import static com.toybeth.dokto.stepper.internal.util.AnimationUtil.ALPHA_OPAQUE;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.viewpager2.widget.ViewPager2;

import com.toybeth.dokto.stepper.R;
import com.toybeth.dokto.stepper.StepperLayout;

/**
 * Feedback stepper type which partially fades the content out.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class ContentFadeStepperFeedbackType implements StepperFeedbackType {

    @NonNull
    private final ViewPager2 mPager;

    @FloatRange(from = 0.0f, to = 1.0f)
    private final float mFadeOutAlpha;

    public ContentFadeStepperFeedbackType(@NonNull StepperLayout stepperLayout) {
        mPager = stepperLayout.findViewById(R.id.ms_stepPager);

//        mPager.setPageTransformer(StepPageTransformerFactory.createPageTransformer(stepperLayout.getContext()));
        mFadeOutAlpha = stepperLayout.getContentFadeAlpha();
    }

    @Override
    public void showProgress(@NonNull String progressMessage) {
        mPager.animate()
                .alpha(mFadeOutAlpha)
                .setDuration(PROGRESS_ANIMATION_DURATION);
    }

    @Override
    public void hideProgress() {
        mPager.animate()
                .alpha(ALPHA_OPAQUE)
                .setDuration(PROGRESS_ANIMATION_DURATION);
    }
}
