/*
 *
 * Copyright 2016 StepStone Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.toybeth.dokto.stepper

import androidx.annotation.UiThread
import com.toybeth.dokto.stepper.StepperLayout.OnNextClickedCallback
import com.toybeth.dokto.stepper.StepperLayout.OnCompleteClickedCallback
import com.toybeth.dokto.stepper.StepperLayout.OnBackClickedCallback

/**
 * A [Step] which can block clicking on the next button/tab
 * and perform some operations before switching to the next step.
 */
interface BlockingStep : Step {
    /**
     * Notifies this step that the next button/tab was clicked, the step was verified
     * and the user can go to the next step. This is so that the current step might perform
     * some last minute operations e.g. a network call before switching to the next step.
     * [StepperLayout.OnNextClickedCallback.goToNextStep] must be called once these operations finish.
     *
     * @param callback callback to call once the user wishes to finally switch to the next step
     */
    @UiThread
    fun onNextClicked(callback: OnNextClickedCallback?)

    /**
     * Notifies this step that the complete button/tab was clicked, the step was verified
     * and the user can complete the flow. This is so that the current step might perform
     * some last minute operations e.g. a network call before completing the flow.
     * [StepperLayout.OnCompleteClickedCallback.complete] must be called once these operations finish.
     *
     * @param callback callback to call once the user wishes to complete the flow
     */
    @UiThread
    fun onCompleteClicked(callback: OnCompleteClickedCallback?)

    /**
     * Notifies this step that the previous button/tab was clicked. This is so that the current step might perform
     * some last minute operations e.g. a network call before switching to previous step.
     * [StepperLayout.OnBackClickedCallback.goToPrevStep] must be called once these operations finish.
     *
     * @param callback callback to call once the user wishes to finally switch to the previous step
     */
    @UiThread
    fun onBackClicked(callback: OnBackClickedCallback?)
}