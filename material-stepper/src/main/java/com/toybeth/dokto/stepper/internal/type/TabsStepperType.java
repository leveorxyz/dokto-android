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

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.toybeth.dokto.stepper.R;
import com.toybeth.dokto.stepper.StepperLayout;
import com.toybeth.dokto.stepper.VerificationError;
import com.toybeth.dokto.stepper.adapter.StepAdapter;
import com.toybeth.dokto.stepper.internal.widget.TabsContainer;
import com.toybeth.dokto.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stepper type which displays horizontal stepper with tabs.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class TabsStepperType extends AbstractStepperType {

    private final TabsContainer mTabsContainer;

    public TabsStepperType(StepperLayout stepperLayout) {
        super(stepperLayout);
        mTabsContainer = (TabsContainer) stepperLayout.findViewById(R.id.ms_stepTabsContainer);
        mTabsContainer.setSelectedColor(stepperLayout.getSelectedColor());
        mTabsContainer.setUnselectedColor(stepperLayout.getUnselectedColor());
        mTabsContainer.setErrorColor(stepperLayout.getErrorColor());
        mTabsContainer.setDividerWidth(stepperLayout.getTabStepDividerWidth());
        mTabsContainer.setListener(stepperLayout);

        if (stepperLayout.isInEditMode()) {
            //noinspection ConstantConditions
            mTabsContainer.setSteps(Arrays.asList(
                    new StepViewModel.Builder(null).setTitle("Step 1").create(),
                    new StepViewModel.Builder(null).setTitle("Step 2").setSubtitle("Optional").create())
            );
            mTabsContainer.updateSteps(0, new SparseArray<VerificationError>(), false);
            mTabsContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStepSelected(int newStepPosition, boolean userTriggeredChange) {
        if (!mStepperLayout.isShowErrorStateEnabled()) {
            mStepErrors.clear();
        }

        mTabsContainer.updateSteps(newStepPosition, mStepErrors, mStepperLayout.isShowErrorMessageEnabled());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewAdapter(@NonNull StepAdapter stepAdapter) {
        super.onNewAdapter(stepAdapter);
        List<StepViewModel> stepViewModels = new ArrayList<>();
        final int stepCount = stepAdapter.getCount();
        for (int i = 0; i < stepCount; i++) {
            stepViewModels.add(stepAdapter.getViewModel(i));
        }
        mTabsContainer.setSteps(stepViewModels);
        mTabsContainer.setVisibility(stepCount > 1 ? View.VISIBLE : View.GONE);
    }
}
