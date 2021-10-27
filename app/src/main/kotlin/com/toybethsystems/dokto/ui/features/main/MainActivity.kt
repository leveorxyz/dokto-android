package com.toybethsystems.dokto.ui.features.main

import androidx.activity.viewModels
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_main
}
