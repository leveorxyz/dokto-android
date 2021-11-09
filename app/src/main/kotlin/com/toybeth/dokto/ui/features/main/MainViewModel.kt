package com.toybeth.dokto.ui.features.main

import com.toybeth.dokto.base.ui.BaseViewModel
import com.toybeth.dokto.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {


}