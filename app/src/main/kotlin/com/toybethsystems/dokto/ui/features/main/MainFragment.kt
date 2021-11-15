package com.toybethsystems.dokto.ui.features.main

import androidx.fragment.app.viewModels
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

}