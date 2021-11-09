package com.toybeth.dokto.ui.features.main

import android.view.LayoutInflater
import android.os.Bundle
import androidx.activity.viewModels
import com.toybeth.dokto.R
import com.toybeth.dokto.base.ui.BaseActivity
import com.toybeth.dokto.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override val bindingInflater: (inflater: LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
}
