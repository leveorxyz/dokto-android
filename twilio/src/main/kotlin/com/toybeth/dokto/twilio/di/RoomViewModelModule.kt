package com.toybeth.dokto.twilio.di

import android.app.Application
import android.content.Context
import com.toybeth.dokto.twilio.ui.call.ParticipantManager
import com.toybeth.dokto.twilio.ui.call.PermissionUtil
import com.toybeth.dokto.twilio.ui.call.RoomViewState
import com.twilio.audioswitch.AudioDevice
import com.twilio.audioswitch.AudioSwitch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RoomViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesPermissionUtil(@ApplicationContext application: Context) = PermissionUtil(application)

    @Provides
    @ViewModelScoped
    fun providesParticipantManager() = ParticipantManager()

    @Provides
    @ViewModelScoped
    fun providesInitialViewState(participantManager: ParticipantManager) = RoomViewState(participantManager.primaryParticipant)

    @Provides
    @ViewModelScoped
    fun providesAudioSwitch(@ApplicationContext application: Context): AudioSwitch =
        AudioSwitch(application,
            loggingEnabled = true,
            preferredDeviceList = listOf(
//                AudioDevice.BluetoothHeadset::class.java,
                AudioDevice.WiredHeadset::class.java,
                AudioDevice.Speakerphone::class.java,
                AudioDevice.Earpiece::class.java))
}