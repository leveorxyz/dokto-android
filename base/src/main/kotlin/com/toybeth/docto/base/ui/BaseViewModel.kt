package com.toybeth.docto.base.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), IViewModel {

    val compositeDisposable = CompositeDisposable()
    val loader = MutableLiveData<Boolean>()
    val message = SingleLiveEvent<String>()

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {

    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}