package com.toybethsystems.dokto.di.module

import com.toybethsystems.dokto.base.data.preference.AppPreference
import com.toybethsystems.dokto.base.data.preference.AppPreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun provideAppPreference(preference: AppPreferenceImpl): AppPreference

}