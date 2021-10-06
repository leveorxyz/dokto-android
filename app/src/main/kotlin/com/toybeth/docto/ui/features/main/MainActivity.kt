package com.toybeth.docto.ui.features.main

import androidx.activity.viewModels
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_main
}
