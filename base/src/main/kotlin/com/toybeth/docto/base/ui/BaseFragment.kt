package com.toybeth.docto.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment() {

    private lateinit var communicator: BaseFragmentCommunicator

    abstract val viewModel: ViewModel

    open val bindingInflater: ((inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> ViewBinding)? = null

    open val composeView: ComposeView? = null

    open val showAppBar: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseFragmentCommunicator) {
            communicator = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycle.addObserver(viewModel)
        return if(composeView != null) {
            composeView!!.apply {
                // Dispose the Composition when viewLifecycleOwner is destroyed
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
                )
            }
        } else {
            bindingInflater?.invoke(inflater, container, false)?.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicator.showOrHideActionBar(showAppBar)
    }

    fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        communicator.startActivity(clz, bundle)
    }

    fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
        communicator.setupActionBar(toolbar, enableBackButton)
    }
}