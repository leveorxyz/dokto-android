package com.toybeth.docto.ui.features.main

import com.toybeth.docto.core.ui.BaseViewModel
import com.toybeth.docto.data.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {


}