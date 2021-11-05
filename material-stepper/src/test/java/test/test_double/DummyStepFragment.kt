package test.test_double

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toybeth.dokto.stepper.Step
import com.toybeth.dokto.stepper.VerificationError

/**
 * A dummy fragment with no view.
 */
open class DummyStepFragment : Fragment(), Step {

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null
    }

    override fun onError(error: VerificationError) {}
}