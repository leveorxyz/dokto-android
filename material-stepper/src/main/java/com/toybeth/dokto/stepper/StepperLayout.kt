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
package com.toybeth.dokto.stepper

import android.content.Context
import android.widget.LinearLayout
import com.toybeth.dokto.stepper.internal.widget.TabsContainer.TabItemListener
import androidx.viewpager2.widget.ViewPager2
import com.toybeth.dokto.stepper.internal.widget.RightNavigationButton
import com.toybeth.dokto.stepper.internal.widget.DottedProgressBar
import com.toybeth.dokto.stepper.internal.widget.ColorableProgressBar
import com.toybeth.dokto.stepper.internal.widget.TabsContainer
import android.content.res.ColorStateList
import com.toybeth.dokto.stepper.internal.type.AbstractStepperType
import com.toybeth.dokto.stepper.internal.feedback.StepperFeedbackType
import com.toybeth.dokto.stepper.adapter.StepAdapter
import com.toybeth.dokto.stepper.internal.util.AnimationUtil
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.toybeth.dokto.stepper.internal.util.TintUtil
import com.toybeth.dokto.stepper.internal.feedback.StepperFeedbackTypeFactory
import android.view.LayoutInflater
import android.view.View.OnTouchListener
import com.toybeth.dokto.stepper.internal.type.StepperTypeFactory
import com.toybeth.dokto.stepper.viewmodel.StepViewModel
import androidx.core.content.res.ResourcesCompat
import com.toybeth.dokto.stepper.internal.widget.pagetransformer.StepPageTransformerFactory
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat

/**
 * Stepper widget implemented according to the [Material documentation](https://www.google.com/design/spec/components/steppers.html).<br></br>
 * It allows for setting three types of steppers:<br></br>
 * - mobile dots stepper,<br></br>
 * - mobile progress bar stepper,<br></br>
 * - horizontal stepper with tabs.<br></br>
 * Include this stepper in the layout XML file and choose a stepper type with `ms_stepperType`.<br></br>
 * Check out `values/attrs.xml - StepperLayout` for a complete list of customisable properties.
 */
class StepperLayout : LinearLayout, TabItemListener {
    /**
     * A listener for events of [StepperLayout].
     */
    interface StepperListener {
        /**
         * Called when all of the steps were completed successfully
         *
         * @param completeButton the complete button that was clicked to complete the flow
         */
        fun onCompleted(completeButton: View?)

        /**
         * Called when a verification error occurs for one of the steps
         *
         * @param verificationError verification error
         */
        fun onError(verificationError: com.toybeth.dokto.stepper.VerificationError?)

        /**
         * Called when the current step position changes
         *
         * @param newStepPosition new step position
         */
        fun onStepSelected(newStepPosition: Int)

        /**
         * Called when the Previous step button was pressed while on the first step
         * (the button is not present by default on first step).
         */
        fun onReturn()

        companion object {
            val NULL: StepperListener = object : StepperListener {
                override fun onCompleted(completeButton: View?) {}
                override fun onError(verificationError: VerificationError?) {}
                override fun onStepSelected(newStepPosition: Int) {}
                override fun onReturn() {}
            }
        }
    }

    abstract inner class AbstractOnButtonClickedCallback {
        val stepperLayout: StepperLayout
            get() = this@StepperLayout
    }

    inner class OnNextClickedCallback : AbstractOnButtonClickedCallback() {
        @UiThread
        fun goToNextStep() {
            val totalStepCount = adapter!!.count
            if (mCurrentStepPosition >= totalStepCount - 1) {
                return
            }
            mCurrentStepPosition++
            onUpdate(mCurrentStepPosition, true)
        }
    }

    inner class OnCompleteClickedCallback : AbstractOnButtonClickedCallback() {
        @UiThread
        fun complete() {
            invalidateCurrentPosition()
            mListener.onCompleted(mCompleteNavigationButton)
        }
    }

    inner class OnBackClickedCallback : AbstractOnButtonClickedCallback() {
        @UiThread
        fun goToPrevStep() {
            if (mCurrentStepPosition <= 0) {
                if (mShowBackButtonOnFirstStep) {
                    mListener.onReturn()
                }
                return
            }
            mCurrentStepPosition--
            onUpdate(mCurrentStepPosition, true)
        }
    }

    private inner class OnBackClickListener : OnClickListener {
        override fun onClick(v: View) {
            onBackClicked()
        }
    }

    private inner class OnNextClickListener : OnClickListener {
        override fun onClick(v: View) {
            onNext()
        }
    }

    private inner class OnCompleteClickListener : OnClickListener {
        override fun onClick(v: View) {
            onComplete()
        }
    }

    private var mPager: ViewPager2? = null
    private var mBackNavigationButton: Button? = null
    private var mNextNavigationButton: RightNavigationButton? = null
    private var mCompleteNavigationButton: RightNavigationButton? = null
    private var mStepNavigation: ViewGroup? = null
    private var mDottedProgressBar: DottedProgressBar? = null
    private var mProgressBar: ColorableProgressBar? = null
    private var mTabsContainer: TabsContainer? = null
    private var mBackButtonColor: ColorStateList? = null
    private var mNextButtonColor: ColorStateList? = null
    private var mCompleteButtonColor: ColorStateList? = null

    @ColorInt
    var unselectedColor = 0
        private set

    @ColorInt
    var selectedColor = 0
        private set

    @ColorInt
    var errorColor = 0
        private set

    @DrawableRes
    private var mBottomNavigationBackground = 0

    @DrawableRes
    private var mBackButtonBackground = 0

    @DrawableRes
    private var mNextButtonBackground = 0

    @DrawableRes
    private var mCompleteButtonBackground = 0
    var tabStepDividerWidth = DEFAULT_TAB_DIVIDER_WIDTH
        private set
    private var mBackButtonText: String? = null
    private var mNextButtonText: String? = null
    private var mCompleteButtonText: String? = null
    private var mShowBackButtonOnFirstStep = false
    private var mShowBottomNavigation = false
    private var mTypeIdentifier = AbstractStepperType.PROGRESS_BAR
    private var mFeedbackTypeMask = StepperFeedbackType.NONE

    /**
     * Getter for mStepAdapter
     *
     * @return mStepAdapter
     */
    var adapter: StepAdapter? = null
        private set
    private var mStepperType: AbstractStepperType? = null
    private var mStepperFeedbackType: StepperFeedbackType? = null

    /**
     * @return An alpha value from 0 to 1.0f to be used for the faded out view if 'content_fade' stepper feedback is set. 0.5f by default.
     */
    @get:FloatRange(from = 0.0, to = 1.0)
    @FloatRange(from = 0.0, to = 1.0)
    var contentFadeAlpha = AnimationUtil.ALPHA_HALF
        private set

    /**
     * @return Background res ID to be used for the overlay on top of the content
     * if 'content_overlay' stepper feedback type is set. 0 if default background should be used.
     */
    @get:DrawableRes
    @DrawableRes
    var contentOverlayBackground = 0
        private set
    private var mCurrentStepPosition = 0
    /**
     * @return true if errors should be displayed when they occur
     */
    /**
     * Set whether the errors should be displayed when they occur or not. Default is `false`.
     *
     * @param showErrorStateEnabled true if the errors should be displayed when they occur, false otherwise
     */
    var isShowErrorStateEnabled = false
    /**
     * @return true if when going backwards the error state from the Tab should be cleared
     */
    /**
     * Set whether when going backwards should clear the error state from the Tab. Default is `false`.
     *
     * @param showErrorStateOnBackEnabled true if navigating backwards should keep the error state, false otherwise
     */
    var isShowErrorStateOnBackEnabled = false
    /**
     * @return true if an error message below step title should appear when an error occurs
     */
    /**
     * Set whether an error message below step title should appear when an error occurs
     *
     * @param showErrorMessageEnabled true if an error message below step title should appear when an error occurs
     */
    var isShowErrorMessageEnabled = false
    /**
     * @return true if step navigation is possible by clicking on the tabs directly, false otherwise
     */
    /**
     * Sets whether step navigation is possible by clicking on the tabs directly. Only applicable for 'tabs' type.
     *
     * @param tabNavigationEnabled true if step navigation is possible by clicking on the tabs directly, false otherwise
     */
    var isTabNavigationEnabled = false

    /**
     * Checks if there's an ongoing operation i.e. if [.showProgress] was called and not followed by [.hideProgress] yet.
     *
     * @return true if in progress, false otherwise
     */
    var isInProgress = false
        private set

    @StyleRes
    private var mStepperLayoutTheme = 0
    private var mListener = StepperListener.NULL

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null) : super(context, attrs) {
        //Fix for issue #60 with AS Preview editor
        init(attrs, if (isInEditMode) 0 else R.attr.ms_stepperStyle)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    override fun setOrientation(orientation: Int) {
        //only vertical orientation is supported
        super.setOrientation(VERTICAL)
    }

    fun setListener(listener: StepperListener) {
        mListener = listener
    }

    /**
     * Sets the new step adapter and updates the stepper layout based on the new adapter.
     *
     * @param stepAdapter step adapter
     */
    fun setAdapter(stepAdapter: StepAdapter) {
        adapter = stepAdapter
        mPager!!.adapter = stepAdapter.pagerAdapter
        mStepperType!!.onNewAdapter(stepAdapter)

        // this is so that the fragments in the adapter can be created BEFORE the onUpdate() method call
        mPager!!.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mPager!!.viewTreeObserver.removeGlobalOnLayoutListener(this)
                onUpdate(mCurrentStepPosition, false)
            }
        })
    }

    /**
     * Sets the new step adapter and updates the stepper layout based on the new adapter.
     *
     * @param stepAdapter         step adapter
     * @param currentStepPosition the initial step position, must be in the range of the adapter item count
     */
    fun setAdapter(stepAdapter: StepAdapter, @IntRange(from = 0) currentStepPosition: Int) {
        mCurrentStepPosition = currentStepPosition
        setAdapter(stepAdapter)
    }

    /**
     * Overrides the default page transformer
     * If you're supporting RTL make sure your [ViewPager.PageTransformer] accounts for it.
     *
     * @param pageTransformer new page transformer
     * @see com.toybeth.dokto.stepper.internal.widget.pagetransformer.StepPageTransformerFactory
     */
    fun setPageTransformer(pageTransformer: ViewPager2.PageTransformer?) {
        mPager!!.setPageTransformer(pageTransformer)
    }

    @UiThread
    override fun onTabClicked(position: Int) {
        if (isTabNavigationEnabled) {
            if (position > mCurrentStepPosition) {
                onNext()
            } else if (position < mCurrentStepPosition) {
                currentStepPosition = position
            }
        }
    }

    /**
     * This is an equivalent of clicking the Next/Complete button from the bottom navigation.
     * Unlike [.setCurrentStepPosition] this actually verifies the step.
     */
    fun proceed() {
        if (isLastPosition(mCurrentStepPosition)) {
            onComplete()
        } else {
            onNext()
        }
    }

    /**
     * To be called when the user wants to go to the previous step.
     */
    fun onBackClicked() {
        val step: Step = findCurrentStep()
        updateErrorFlagWhenGoingBack()
        val onBackClickedCallback: OnBackClickedCallback = OnBackClickedCallback()
        if (step is BlockingStep) {
            step.onBackClicked(onBackClickedCallback)
        } else {
            onBackClickedCallback.goToPrevStep()
        }
    }
    /**
     * Returns the position of the currently selected step.
     *
     * @return position of the currently selected step
     */
    /**
     * Sets the current step to the one at the provided index.
     * This does not verify the current step.
     *
     * @param currentStepPosition new current step position
     */
    var currentStepPosition: Int
        get() = mCurrentStepPosition
        set(currentStepPosition) {
            val previousStepPosition = mCurrentStepPosition
            if (currentStepPosition < previousStepPosition) {
                updateErrorFlagWhenGoingBack()
            }
            mCurrentStepPosition = currentStepPosition
            onUpdate(currentStepPosition, true)
        }

    /**
     * Sets whether the Next button in the bottom navigation bar should be in the
     * 'verification failed' state i.e. still clickable but with an option to display it
     * differently to indicate to the user that he cannot go to the next step yet.
     *
     * @param verificationFailed false if verification failed, true otherwise
     */
    fun setNextButtonVerificationFailed(verificationFailed: Boolean) {
        mNextNavigationButton!!.setVerificationFailed(verificationFailed)
    }

    /**
     * Sets whether the Complete button in the bottom navigation bar should be in the
     * 'verification failed' state i.e. still clickable but with an option to display it
     * differently to indicate to the user that he cannot finish the process yet.
     *
     * @param verificationFailed false if verification failed, true otherwise
     */
    fun setCompleteButtonVerificationFailed(verificationFailed: Boolean) {
        mCompleteNavigationButton!!.setVerificationFailed(verificationFailed)
    }

    /**
     * Sets whether the Next button in the bottom navigation bar should be enabled (clickable).
     * setting this to *false* will make it unclickable.
     *
     * @param enabled true if the button should be clickable, false otherwise
     */
    fun setNextButtonEnabled(enabled: Boolean) {
        mNextNavigationButton!!.isEnabled = enabled
    }

    /**
     * Sets whether the Complete button in the bottom navigation bar should be enabled (clickable).
     * setting this to *false* will make it unclickable.
     *
     * @param enabled true if the button should be clickable, false otherwise
     */
    fun setCompleteButtonEnabled(enabled: Boolean) {
        mCompleteNavigationButton!!.isEnabled = enabled
    }

    /**
     * Sets whether the Back button in the bottom navigation bar should be enabled (clickable).
     * setting this to *false* will make it unclickable.
     *
     * @param enabled true if the button should be clickable, false otherwise
     */
    fun setBackButtonEnabled(enabled: Boolean) {
        mBackNavigationButton!!.isEnabled = enabled
    }

    /**
     * Set whether the bottom navigation bar (with Back/Next/Complete buttons) should be visible.
     * *true* by default.
     *
     * @param showBottomNavigation true if bottom navigation should be visible, false otherwise
     */
    fun setShowBottomNavigation(showBottomNavigation: Boolean) {
        mStepNavigation!!.visibility = if (showBottomNavigation) VISIBLE else GONE
    }

    /**
     * Set whether when going backwards should clear the error state from the Tab. Default is `false`.
     *
     * @param showErrorStateOnBack true if navigating backwards should keep the error state, false otherwise
     */
    @Deprecated("see {@link #setShowErrorStateOnBackEnabled(boolean)}")
    fun setShowErrorStateOnBack(showErrorStateOnBack: Boolean) {
        isShowErrorStateOnBackEnabled = showErrorStateOnBack
    }

    /**
     * Set whether the errors should be displayed when they occur or not. Default is `false`.
     *
     * @param showErrorState true if the errors should be displayed when they occur, false otherwise
     */
    @Deprecated("see {@link #setShowErrorStateEnabled(boolean)}")
    fun setShowErrorState(showErrorState: Boolean) {
        isShowErrorStateEnabled = showErrorState
    }

    /**
     * Changes the text and compound drawable color of the Next bottom navigation button.
     *
     * @param newButtonColor new color state list
     */
    fun setNextButtonColor(newButtonColor: ColorStateList) {
        mNextButtonColor = newButtonColor
        TintUtil.tintTextView(mNextNavigationButton!!, mNextButtonColor)
    }

    /**
     * Changes the text and compound drawable color of the Complete bottom navigation button.
     *
     * @param newButtonColor new color state list
     */
    fun setCompleteButtonColor(newButtonColor: ColorStateList) {
        mCompleteButtonColor = newButtonColor
        TintUtil.tintTextView(mCompleteNavigationButton!!, mCompleteButtonColor)
    }

    /**
     * Changes the text and compound drawable color of the Back bottom navigation button.
     *
     * @param newButtonColor new color state list
     */
    fun setBackButtonColor(newButtonColor: ColorStateList) {
        mBackButtonColor = newButtonColor
        TintUtil.tintTextView(mBackNavigationButton!!, mBackButtonColor)
    }

    /**
     * Changes the text and compound drawable color of the Next bottom navigation button.
     *
     * @param newButtonColor new color int
     */
    fun setNextButtonColor(@ColorInt newButtonColor: Int) {
        setNextButtonColor(ColorStateList.valueOf(newButtonColor))
    }

    /**
     * Changes the text and compound drawable color of the Complete bottom navigation button.
     *
     * @param newButtonColor new color int
     */
    fun setCompleteButtonColor(@ColorInt newButtonColor: Int) {
        setCompleteButtonColor(ColorStateList.valueOf(newButtonColor))
    }

    /**
     * Changes the text and compound drawable color of the Back bottom navigation button.
     *
     * @param newButtonColor new color int
     */
    fun setBackButtonColor(@ColorInt newButtonColor: Int) {
        setBackButtonColor(ColorStateList.valueOf(newButtonColor))
    }

    /**
     * Updates the error state in the UI.
     * It does nothing if showing error state is disabled.
     * This is used internally to show the error on tabs.
     *
     * @param error not null if error should be shown, null otherwise
     * @see .setShowErrorStateEnabled
     */
    fun updateErrorState(error: VerificationError?) {
        updateError(error)
        if (isShowErrorStateEnabled) {
            invalidateCurrentPosition()
        }
    }

    /**
     * Set the number of steps that should be retained to either side of the
     * current step in the view hierarchy in an idle state. Steps beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many steps will be kept offscreen in an idle state.
     * @see ViewPager.setOffscreenPageLimit
     */
    fun setOffscreenPageLimit(limit: Int) {
        mPager!!.offscreenPageLimit = limit
    }

    /**
     * Shows a progress indicator if not already shown. This does not have to be a progress bar and it depends on chosen stepper feedback types.
     *
     * @param progressMessage optional progress message if supported by the selected types
     */
    fun showProgress(progressMessage: String) {
        if (!isInProgress) {
            mStepperFeedbackType!!.showProgress(progressMessage)
            isInProgress = true
        }
    }

    /**
     * Hides the progress indicator if visible.
     */
    fun hideProgress() {
        if (isInProgress) {
            isInProgress = false
            mStepperFeedbackType!!.hideProgress()
        }
    }

    /**
     * Sets the mask for the stepper feedback type.
     *
     * @param feedbackTypeMask step feedback type mask, should contain one or more flags from [StepperFeedbackType]
     */
    fun setFeedbackType(feedbackTypeMask: Int) {
        mFeedbackTypeMask = feedbackTypeMask
        mStepperFeedbackType = StepperFeedbackTypeFactory.createType(mFeedbackTypeMask, this)
    }

    private fun init(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        initDefaultValues()
        extractValuesFromAttributes(attrs, defStyleAttr)
        val context = context
        val contextThemeWrapper = ContextThemeWrapper(context, context.theme)
        contextThemeWrapper.setTheme(mStepperLayoutTheme)
        LayoutInflater.from(contextThemeWrapper).inflate(R.layout.ms_stepper_layout, this, true)
        orientation = VERTICAL
        bindViews()
        mPager!!.setOnTouchListener { view: View?, motionEvent: MotionEvent? -> true }
        initNavigation()
        mDottedProgressBar!!.visibility = GONE
        mProgressBar!!.visibility = GONE
        mTabsContainer!!.visibility = GONE
        mStepNavigation!!.visibility = if (mShowBottomNavigation) VISIBLE else GONE
        mStepperType = StepperTypeFactory.createType(mTypeIdentifier, this)
        mStepperFeedbackType = StepperFeedbackTypeFactory.createType(mFeedbackTypeMask, this)
    }

    private fun initNavigation() {
        if (mBottomNavigationBackground != 0) {
            mStepNavigation!!.setBackgroundResource(mBottomNavigationBackground)
        }
        mBackNavigationButton!!.text = mBackButtonText
        mNextNavigationButton!!.text = mNextButtonText
        mCompleteNavigationButton!!.text = mCompleteButtonText
        setBackgroundIfPresent(mBackButtonBackground, mBackNavigationButton)
        setBackgroundIfPresent(mNextButtonBackground, mNextNavigationButton)
        setBackgroundIfPresent(mCompleteButtonBackground, mCompleteNavigationButton)
        mBackNavigationButton!!.setOnClickListener(OnBackClickListener())
        mNextNavigationButton!!.setOnClickListener(OnNextClickListener())
        mCompleteNavigationButton!!.setOnClickListener(OnCompleteClickListener())
    }

    private fun setCompoundDrawablesForNavigationButtons(
        @DrawableRes backDrawableResId: Int,
        @DrawableRes nextDrawableResId: Int
    ) {
        val chevronStartDrawable =
            if (backDrawableResId != StepViewModel.NULL_DRAWABLE) ResourcesCompat.getDrawable(
                context.resources, backDrawableResId, null
            ) else null
        val chevronEndDrawable =
            if (nextDrawableResId != StepViewModel.NULL_DRAWABLE) ResourcesCompat.getDrawable(
                context.resources, nextDrawableResId, null
            ) else null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mBackNavigationButton!!.setCompoundDrawablesRelativeWithIntrinsicBounds(
                chevronStartDrawable,
                null,
                null,
                null
            )
        } else {
            mBackNavigationButton!!.setCompoundDrawablesWithIntrinsicBounds(
                chevronStartDrawable,
                null,
                null,
                null
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mNextNavigationButton!!.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                chevronEndDrawable,
                null
            )
        } else {
            mNextNavigationButton!!.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                chevronEndDrawable,
                null
            )
        }
        TintUtil.tintTextView(mBackNavigationButton!!, mBackButtonColor)
        TintUtil.tintTextView(mNextNavigationButton!!, mNextButtonColor)
        TintUtil.tintTextView(mCompleteNavigationButton!!, mCompleteButtonColor)
    }

    private fun setBackgroundIfPresent(@DrawableRes backgroundRes: Int, view: View?) {
        if (backgroundRes != 0) {
            view!!.setBackgroundResource(backgroundRes)
        }
    }

    private fun bindViews() {
        mPager = findViewById(R.id.ms_stepPager)
        mPager?.isUserInputEnabled = false
        mPager?.setPageTransformer(StepPageTransformerFactory.createPageTransformer(resources))
        mBackNavigationButton = findViewById(R.id.ms_stepPrevButton)
        mNextNavigationButton = findViewById(R.id.ms_stepNextButton)
        mCompleteNavigationButton = findViewById(R.id.ms_stepCompleteButton)
        mStepNavigation = findViewById(R.id.ms_bottomNavigation)
        mDottedProgressBar = findViewById(R.id.ms_stepDottedProgressBar)
        mProgressBar = findViewById(R.id.ms_stepProgressBar)
        mTabsContainer = findViewById(R.id.ms_stepTabsContainer)
    }

    private fun extractValuesFromAttributes(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs, R.styleable.StepperLayout, defStyleAttr, 0
            )
            if (a.hasValue(R.styleable.StepperLayout_ms_backButtonColor)) {
                mBackButtonColor = a.getColorStateList(R.styleable.StepperLayout_ms_backButtonColor)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_nextButtonColor)) {
                mNextButtonColor = a.getColorStateList(R.styleable.StepperLayout_ms_nextButtonColor)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_completeButtonColor)) {
                mCompleteButtonColor =
                    a.getColorStateList(R.styleable.StepperLayout_ms_completeButtonColor)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_activeStepColor)) {
                selectedColor =
                    a.getColor(R.styleable.StepperLayout_ms_activeStepColor, selectedColor)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_inactiveStepColor)) {
                unselectedColor =
                    a.getColor(R.styleable.StepperLayout_ms_inactiveStepColor, unselectedColor)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_errorColor)) {
                errorColor = a.getColor(R.styleable.StepperLayout_ms_errorColor, errorColor)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_bottomNavigationBackground)) {
                mBottomNavigationBackground =
                    a.getResourceId(R.styleable.StepperLayout_ms_bottomNavigationBackground, 0)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_backButtonBackground)) {
                mBackButtonBackground =
                    a.getResourceId(R.styleable.StepperLayout_ms_backButtonBackground, 0)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_nextButtonBackground)) {
                mNextButtonBackground =
                    a.getResourceId(R.styleable.StepperLayout_ms_nextButtonBackground, 0)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_completeButtonBackground)) {
                mCompleteButtonBackground =
                    a.getResourceId(R.styleable.StepperLayout_ms_completeButtonBackground, 0)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_backButtonText)) {
                mBackButtonText = a.getString(R.styleable.StepperLayout_ms_backButtonText)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_nextButtonText)) {
                mNextButtonText = a.getString(R.styleable.StepperLayout_ms_nextButtonText)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_completeButtonText)) {
                mCompleteButtonText = a.getString(R.styleable.StepperLayout_ms_completeButtonText)
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_tabStepDividerWidth)) {
                tabStepDividerWidth =
                    a.getDimensionPixelOffset(R.styleable.StepperLayout_ms_tabStepDividerWidth, -1)
            }
            mShowBackButtonOnFirstStep =
                a.getBoolean(R.styleable.StepperLayout_ms_showBackButtonOnFirstStep, false)
            mShowBottomNavigation =
                a.getBoolean(R.styleable.StepperLayout_ms_showBottomNavigation, true)
            isShowErrorStateEnabled =
                a.getBoolean(R.styleable.StepperLayout_ms_showErrorState, false)
            isShowErrorStateEnabled = a.getBoolean(
                R.styleable.StepperLayout_ms_showErrorStateEnabled,
                isShowErrorStateEnabled
            )
            if (a.hasValue(R.styleable.StepperLayout_ms_stepperType)) {
                mTypeIdentifier = a.getInt(
                    R.styleable.StepperLayout_ms_stepperType,
                    AbstractStepperType.PROGRESS_BAR
                )
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_stepperFeedbackType)) {
                mFeedbackTypeMask = a.getInt(
                    R.styleable.StepperLayout_ms_stepperFeedbackType,
                    StepperFeedbackType.NONE
                )
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_stepperFeedback_contentFadeAlpha)) {
                contentFadeAlpha = a.getFloat(
                    R.styleable.StepperLayout_ms_stepperFeedback_contentFadeAlpha,
                    AnimationUtil.ALPHA_HALF
                )
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_stepperFeedback_contentOverlayBackground)) {
                contentOverlayBackground = a.getResourceId(
                    R.styleable.StepperLayout_ms_stepperFeedback_contentOverlayBackground,
                    0
                )
            }
            isShowErrorStateOnBackEnabled =
                a.getBoolean(R.styleable.StepperLayout_ms_showErrorStateOnBack, false)
            isShowErrorStateOnBackEnabled = a.getBoolean(
                R.styleable.StepperLayout_ms_showErrorStateOnBackEnabled,
                isShowErrorStateOnBackEnabled
            )
            isShowErrorMessageEnabled =
                a.getBoolean(R.styleable.StepperLayout_ms_showErrorMessageEnabled, false)
            isTabNavigationEnabled =
                a.getBoolean(R.styleable.StepperLayout_ms_tabNavigationEnabled, true)
            mStepperLayoutTheme = a.getResourceId(
                R.styleable.StepperLayout_ms_stepperLayoutTheme,
                R.style.MSDefaultStepperLayoutTheme
            )
            a.recycle()
        }
    }

    private fun initDefaultValues() {
        mCompleteButtonColor =
            ContextCompat.getColorStateList(context, R.color.ms_bottomNavigationButtonTextColor)
        mNextButtonColor = mCompleteButtonColor
        mBackButtonColor = mNextButtonColor
        selectedColor = ContextCompat.getColor(context, R.color.ms_selectedColor)
        unselectedColor = ContextCompat.getColor(context, R.color.ms_unselectedColor)
        errorColor = ContextCompat.getColor(context, R.color.ms_errorColor)
        mBackButtonText = context.getString(R.string.ms_back)
        mNextButtonText = context.getString(R.string.ms_next)
        mCompleteButtonText = context.getString(R.string.ms_complete)
    }

    private fun isLastPosition(position: Int): Boolean {
        return position == adapter!!.count - 1
    }

    private fun findCurrentStep(): Step {
        return adapter!!.findStep(mCurrentStepPosition)
    }

    private fun updateErrorFlagWhenGoingBack() {
        updateError(
            if (isShowErrorStateOnBackEnabled) mStepperType!!.getErrorAtPosition(
                mCurrentStepPosition
            ) else null
        )
    }

    @UiThread
    private fun onNext() {
        val step: Step = findCurrentStep()
        if (verifyCurrentStep(step)) {
            invalidateCurrentPosition()
            return
        }
        val onNextClickedCallback: OnNextClickedCallback = OnNextClickedCallback()
        if (step is BlockingStep) {
            step.onNextClicked(onNextClickedCallback)
        } else {
            onNextClickedCallback.goToNextStep()
        }
    }

    private fun invalidateCurrentPosition() {
        mStepperType!!.onStepSelected(mCurrentStepPosition, false)
    }

    private fun verifyCurrentStep(step: Step): Boolean {
        val verificationError: VerificationError? = step.verifyStep()
        var result = false
        if (verificationError != null) {
            onError(verificationError)
            result = true
        }
        updateError(verificationError)
        return result
    }

    private fun updateError(error: VerificationError?) {
        mStepperType!!.setError(mCurrentStepPosition, error)
    }

    private fun onError(verificationError: VerificationError) {
        val step: Step = findCurrentStep()
        if (step != null) {
            step.onError(verificationError)
        }
        mListener.onError(verificationError)
    }

    private fun onComplete() {
        val step: Step = findCurrentStep()
        if (verifyCurrentStep(step)) {
            invalidateCurrentPosition()
            return
        }
        val onCompleteClickedCallback: OnCompleteClickedCallback = OnCompleteClickedCallback()
        if (step is BlockingStep) {
            step.onCompleteClicked(
                onCompleteClickedCallback
            )
        } else {
            onCompleteClickedCallback.complete()
        }
    }

    private fun onUpdate(newStepPosition: Int, userTriggeredChange: Boolean) {
        mPager!!.currentItem = newStepPosition
        val isLast = isLastPosition(newStepPosition)
        val isFirst = newStepPosition == 0
        val viewModel = adapter!!.getViewModel(newStepPosition)
        val backButtonTargetVisibility =
            if (isFirst && !mShowBackButtonOnFirstStep || !viewModel.isBackButtonVisible) GONE else VISIBLE
        val nextButtonVisibility = if (isLast || !viewModel.isEndButtonVisible) GONE else VISIBLE
        val completeButtonVisibility =
            if (!isLast || !viewModel.isEndButtonVisible) GONE else VISIBLE
        AnimationUtil.fadeViewVisibility(
            mNextNavigationButton!!,
            nextButtonVisibility,
            userTriggeredChange
        )
        AnimationUtil.fadeViewVisibility(
            mCompleteNavigationButton!!,
            completeButtonVisibility,
            userTriggeredChange
        )
        AnimationUtil.fadeViewVisibility(
            mBackNavigationButton!!,
            backButtonTargetVisibility,
            userTriggeredChange
        )
        updateBackButton(viewModel)
        updateEndButton(
            viewModel.endButtonLabel,
            if (isLast) mCompleteButtonText else mNextButtonText,
            (if (isLast) mCompleteNavigationButton else mNextNavigationButton)!!
        )
        setCompoundDrawablesForNavigationButtons(
            viewModel.backButtonStartDrawableResId,
            viewModel.nextButtonEndDrawableResId
        )
        mStepperType!!.onStepSelected(newStepPosition, userTriggeredChange)
        mListener.onStepSelected(newStepPosition)
        val step: Step? = adapter!!.findStep(newStepPosition)
        if (step != null) {
            step.onSelected()
        }
    }

    private fun updateEndButton(
        endButtonTextForStep: CharSequence?,
        defaultEndButtonText: CharSequence?,
        endButton: TextView
    ) {
        if (endButtonTextForStep == null) {
            endButton.text = defaultEndButtonText
        } else {
            endButton.text = endButtonTextForStep
        }
    }

    private fun updateBackButton(viewModel: StepViewModel) {
        val backButtonTextForStep = viewModel.backButtonLabel
        if (backButtonTextForStep == null) {
            mBackNavigationButton!!.text = mBackButtonText
        } else {
            mBackNavigationButton!!.text = backButtonTextForStep
        }
    }

    companion object {
        const val DEFAULT_TAB_DIVIDER_WIDTH = -1
    }
}