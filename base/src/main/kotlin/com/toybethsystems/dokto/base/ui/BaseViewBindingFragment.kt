package com.toybethsystems.dokto.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<ViewModel : BaseViewModel, Binding: ViewBinding> : BaseFragment<ViewModel>() {

    private lateinit var communicator: BaseFragmentCommunicator
    lateinit var binding: Binding

    abstract val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> Binding

    override val bindingInflater: ((inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> ViewBinding)?
        get() = inflater

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
        binding = inflater(inflater, container, false)
        return binding.root
    }
}