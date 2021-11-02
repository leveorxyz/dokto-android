package com.toybeth.dokto.twilio.ui.common

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.toybeth.docto.base.ui.BaseViewModel
import com.toybeth.docto.base.utils.extensions.launchIOWithExceptionHandler
import com.toybeth.dokto.twilio.data.TwilioCallRepository
import com.twilio.video.LocalVideoTrack
import com.twilio.video.RemoteVideoTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TwilioCallViewModel @Inject constructor(
    private val repository: TwilioCallRepository
) : BaseViewModel() {

    val mirror = MediatorLiveData<Boolean>()
    val participantDisconnectedLiveData = MediatorLiveData<Boolean>()
    val subscribedVideoTrackLiveData = MediatorLiveData<RemoteVideoTrack>()
    val unsubscribedVideoTrackLiveData = MediatorLiveData<RemoteVideoTrack>()
    val localVideoTrackLiveData = MediatorLiveData<LocalVideoTrack?>()
    val audioEnabled = MediatorLiveData<Boolean>()
    val videoEnabled = MediatorLiveData<Boolean>()

    init {
        mirror.addSource(repository.backCameraEnabled) {
            mirror.postValue(it)
        }
        participantDisconnectedLiveData.addSource(repository.participantDisconnectedLiveData) {
            participantDisconnectedLiveData.postValue(it)
        }
        subscribedVideoTrackLiveData.addSource(repository.subscribedVideoTrackLiveData) {
            subscribedVideoTrackLiveData.postValue(it)
        }
        unsubscribedVideoTrackLiveData.addSource(repository.unsubscribedVideoTrackLiveData) {
            unsubscribedVideoTrackLiveData.postValue(it)
        }
        localVideoTrackLiveData.addSource(repository.localVideoTrackLiveData) {
            localVideoTrackLiveData.postValue(it)
        }
        audioEnabled.addSource(repository.audioEnabled) {
            audioEnabled.postValue(it)
        }
        videoEnabled.addSource(repository.videoEnabled) {
            videoEnabled.postValue(it)
        }
    }

    fun initializeTwilio() {
        repository.initializeTwilio()
    }

    fun connectToRoom(roomName: String) {
        viewModelScope.launchIOWithExceptionHandler({
            repository.connectToRoom(roomName)
        }, {
            it.printStackTrace()
        })
    }

    fun pause() {
        repository.pause()
    }

    fun destroy() {
        repository.destroy()
    }

    fun toggleLocalVideoStream() {
        repository.toggleLocalVideo()
    }

    fun toggleLocalAudioStream() {
        repository.toggleLocalAudio()
    }

    fun checkPermissionForCameraAndMicrophone(): Boolean {
        return repository.checkPermissionForCameraAndMicrophone()
    }

    fun switchCamera() {
        repository.switchCamera()
    }

    fun endCall() {
        repository.disconnect()
    }
}