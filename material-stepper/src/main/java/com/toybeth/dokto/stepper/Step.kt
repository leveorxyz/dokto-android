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

/**
 * A base step interface which all [StepperLayout] steps must implement.
 */
interface Step {
    /**
     * Checks if the stepper can go to the next step after this step.<br></br>
     * **This does not mean the user clicked on the Next/Complete button.**<br></br>
     * If the user clicked the Next/Complete button and wants to be informed of that error
     * he should handle this in [.onError].
     *
     * @return the cause of the validation failure or *null* if step was validated successfully
     */
    fun verifyStep(): VerificationError?
    //
    //    void verifyAsync();
    /**
     * Called when this step gets selected in the the stepper layout.
     */
    fun onSelected()

    /**
     * Called when the user clicked on the Next/Complete button and the step verification failed.
     *
     * @param error the cause of the validation failure
     */
    fun onError(error: VerificationError)
}