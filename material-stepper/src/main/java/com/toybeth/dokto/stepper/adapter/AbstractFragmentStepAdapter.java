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

package com.toybeth.dokto.stepper.adapter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.toybeth.dokto.stepper.Step;
import com.toybeth.dokto.stepper.viewmodel.StepViewModel;

/**
 * A base adapter class which returns step fragments to use inside of the {@link com.toybeth.dokto.stepper.StepperLayout}.
 */
public abstract class AbstractFragmentStepAdapter
        extends FragmentStateAdapter
        implements StepAdapter {

    @NonNull
    private final FragmentManager mFragmentManager;

    @NonNull
    protected final Context context;

    public AbstractFragmentStepAdapter(
            @NonNull FragmentManager fm,
            @NonNull Lifecycle lifecycle,
            @NonNull Context context
    ) {
        super(fm, lifecycle);
        this.mFragmentManager = fm;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (Fragment) createStep(position);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Step findStep(@IntRange(from = 0) int position) {
        String fragmentTag = "f" + this.getItemId(position);
        return (Step) mFragmentManager.findFragmentByTag(fragmentTag);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context).create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final FragmentStateAdapter getPagerAdapter() {
        return this;
    }

    @Override
    public int getItemCount() {
        return getCount();
    }
}
