package com.toybethsystems.dokto.ui.features.main

import com.toybethsystems.dokto.base.ui.BaseViewModel
import com.toybethsystems.dokto.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {


}