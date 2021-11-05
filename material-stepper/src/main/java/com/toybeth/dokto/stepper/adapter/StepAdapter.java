package com.toybeth.dokto.stepper.adapter;


import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.toybeth.dokto.stepper.Step;
import com.toybeth.dokto.stepper.viewmodel.StepViewModel;

/**
 * Interface to be used as model to {@link com.toybeth.dokto.stepper.StepperLayout}.
 */
public interface StepAdapter {

    /**
     * Create each step of the {@link com.toybeth.dokto.stepper.StepperLayout}.
     *
     * @param position The position of the {@link PagerAdapter} to be used inside the {@link androidx.viewpager.widget.ViewPager}.
     * @return the step to be used inside the {@link com.toybeth.dokto.stepper.StepperLayout}.
     */
    Step createStep(@IntRange(from = 0) int position);

    /**
     * Finds the given step without creating it.
     *
     * @param position step position
     * @return step fragment
     * @see FragmentPagerAdapter#makeFragmentName(int, long)
     */
    Step findStep(@IntRange(from = 0) int position);

    /**
     * Returns the view information to be used to display the step.
     *
     * @param position step position
     * @return view information
     */
    @NonNull
    StepViewModel getViewModel(@IntRange(from = 0) int position);

    /**
     * Get the step count.
     *
     * @return the quantity of steps
     */
    @IntRange(from = 0)
    int getCount();

    /**
     * Method for internal purpose. Should not be inherited.
     *
     * @return the adapter to be used in the {@link ViewPager}.
     */
    FragmentStateAdapter getPagerAdapter();
}
