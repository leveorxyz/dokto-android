package com.toybethsystems.dokto.ui.features.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseActivity
import com.toybethsystems.dokto.databinding.ActivityMainBinding
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
