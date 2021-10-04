package com.toybeth.docto.ui.features.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.toybeth.docto.R
import com.toybeth.docto.core.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}