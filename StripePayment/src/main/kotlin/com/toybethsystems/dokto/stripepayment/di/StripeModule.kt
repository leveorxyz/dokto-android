package com.toybethsystems.dokto.stripepayment.di

import android.content.Context
import com.toybethsystems.dokto.base.data.network.NetworkFactory
import com.toybethsystems.dokto.stripepayment.BuildConfig
import com.toybethsystems.dokto.stripepayment.data.StripeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StripeModule {

    @Provides
    @Singleton
    fun provideMessageNetworkService(@ApplicationContext context: Context): StripeApiService {
        return NetworkFactory.createServiceForCoroutine(
            context,
            StripeApiService::class.java,
            BuildConfig.BASE_URL,
            "Basic " + BuildConfig.STRIPE_API_KEY
        )
    }
}