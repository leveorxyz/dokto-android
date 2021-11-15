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

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.viewpager2.widget.ViewPager2;

import com.toybeth.dokto.stepper.R;
import com.toybeth.dokto.stepper.StepperLayout;

/**
 * Feedback stepper type which intercepts touch events on the steps' content and ignores them.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class DisabledContentInteractionStepperFeedbackType implements StepperFeedbackType {

    @NonNull
    private final ViewPager2 mStepPager;

    public DisabledContentInteractionStepperFeedbackType(@NonNull StepperLayout stepperLayout) {
        mStepPager = stepperLayout.findViewById(R.id.ms_stepPager);
    }

    @Override
    public void showProgress(@NonNull String progressMessage) {
        setContentInteractionEnabled(false);
    }

    @Override
    public void hideProgress() {
        setContentInteractionEnabled(true);
    }

    private void setContentInteractionEnabled(boolean enabled) {
        mStepPager.setUserInputEnabled(enabled);
    }

}
