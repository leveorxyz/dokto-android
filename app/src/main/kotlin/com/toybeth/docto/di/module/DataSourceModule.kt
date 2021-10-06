package com.toybeth.docto.di.module

import com.toybeth.docto.base.data.preference.AppPreference
import com.toybeth.docto.base.data.preference.AppPreferenceImpl
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