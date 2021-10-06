package com.toybeth.docto.di.module

import android.content.Context
import com.toybeth.docto.base.data.network.NetworkFactory
import com.toybeth.docto.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMessageNetworkService(@ApplicationContext context: Context): ApiService {
        return NetworkFactory.createService(
            context,
            ApiService::class.java
        )
    }
}