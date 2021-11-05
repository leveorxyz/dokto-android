package com.toybeth.dokto.stepper.adapter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.toybeth.dokto.stepper.viewmodel.StepViewModel;

/**
 * A base adapter class which returns step to use inside of the {@link com.toybeth.dokto.stepper.StepperLayout}.
 * This class is intended to be inherited if you need to use {@link com.toybeth.dokto.stepper.StepperLayout} without fragments.
 * Otherwise, you should use {@link AbstractFragmentStepAdapter}
 */
public abstract class AbstractStepAdapter
        extends FragmentStateAdapter
        implements StepAdapter {

    @NonNull
    protected final Context context;

    public AbstractStepAdapter(
            @NonNull FragmentManager fm,
            @NonNull Lifecycle lifecycle,
            @NonNull Context context
    ) {
        super(fm, lifecycle);
        this.context = context;
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
}
