package com.toybeth.dokto.base.ui

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import com.toybeth.dokto.base.ui.uiutils.AnimState
import com.toybeth.dokto.base.utils.SingleLiveEvent
import com.toybeth.dokto.base.utils.extensions.launchIOWithExceptionHandler
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), IViewModel {

    private val screenAnimStateMutableLiveData = MutableLiveData<AnimState>()
    private var createCalled = false
    val compositeDisposable = CompositeDisposable()
    val loader = MutableLiveData<Boolean>()
    val message = SingleLiveEvent<String>()
    val screenAnimState: LiveData<AnimState>
        get() = screenAnimStateMutableLiveData

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {

    }

    override fun onCreate() {
        createCalled = true
        enterToScreen()
    }

    override fun onDestroy() {
        popExitFromScreen()
    }

    override fun onStart() {

    }

    override fun onStop() {

    }



    override fun onResume() {
        if(!createCalled) {
            popEnterToScreen()
        } else {
            createCalled = false
        }
    }

    override fun onPause() {
        exitFromScreen()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun enterToScreen() {
        viewModelScope.launchIOWithExceptionHandler({
            screenAnimStateMutableLiveData.postValue(AnimState.ENTER)
        }, {
            it.printStackTrace()
        })
    }

    fun popEnterToScreen() {
        viewModelScope.launchIOWithExceptionHandler({
            screenAnimStateMutableLiveData.postValue(AnimState.POPENTER)
        }, {
            it.printStackTrace()
        })
    }

    fun exitFromScreen() {
        viewModelScope.launchIOWithExceptionHandler({
            screenAnimStateMutableLiveData.postValue(AnimState.EXIT)
        }, {
            it.printStackTrace()
        })
    }

    fun popExitFromScreen() {
        viewModelScope.launchIOWithExceptionHandler({
            screenAnimStateMutableLiveData.postValue(AnimState.POPEXIT)
        }, {
            it.printStackTrace()
        })
    }
}