/*
Copyright 2016 StepStone Services

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

package com.toybeth.dokto.stepper.internal.type;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.toybeth.dokto.stepper.R;
import com.toybeth.dokto.stepper.StepperLayout;
import com.toybeth.dokto.stepper.adapter.StepAdapter;
import com.toybeth.dokto.stepper.internal.widget.DottedProgressBar;

/**
 * Stepper type which displays mobile step dots.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class DotsStepperType extends AbstractStepperType {

    private static final int EDIT_MODE_DOT_COUNT = 3;

    private final DottedProgressBar mDottedProgressBar;

    public DotsStepperType(StepperLayout stepperLayout) {
        super(stepperLayout);
        mDottedProgressBar = stepperLayout.findViewById(R.id.ms_stepDottedProgressBar);

        mDottedProgressBar.setSelectedColor(getSelectedColor());
        mDottedProgressBar.setUnselectedColor(getUnselectedColor());

        if (stepperLayout.isInEditMode()) {
            mDottedProgressBar.setDotCount(EDIT_MODE_DOT_COUNT);
            mDottedProgressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStepSelected(int newStepPosition, boolean userTriggeredChange) {
        mDottedProgressBar.setCurrent(newStepPosition, userTriggeredChange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewAdapter(@NonNull StepAdapter stepAdapter) {
        super.onNewAdapter(stepAdapter);
        final int stepCount = stepAdapter.getCount();
        mDottedProgressBar.setDotCount(stepCount);
        mDottedProgressBar.setVisibility(stepCount > 1 ? View.VISIBLE : View.GONE);
    }
}
