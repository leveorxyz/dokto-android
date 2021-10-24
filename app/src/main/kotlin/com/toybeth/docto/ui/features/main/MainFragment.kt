package com.toybeth.docto.ui.features.main

import androidx.fragment.app.viewModels
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
}