package com.toybeth.dokto.paystack.di

import android.content.Context
import com.toybeth.docto.base.data.network.NetworkFactory
import com.toybeth.dokto.paystack.BuildConfig
import com.toybeth.dokto.paystack.data.PayStackApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PayStackPaymentModule {

    @Provides
    @Singleton
    fun provideMessageNetworkService(@ApplicationContext context: Context): PayStackApiService {
        return NetworkFactory.createServiceForCoroutine(
            context,
            PayStackApiService::class.java,
            BuildConfig.BASE_URL,
            "Bearer " + BuildConfig.PAYSTACK_SECRET_KEY
        )
    }
}
