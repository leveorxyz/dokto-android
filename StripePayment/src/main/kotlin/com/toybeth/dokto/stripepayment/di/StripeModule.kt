package com.toybeth.dokto.stripepayment.di

import android.content.Context
import com.toybeth.docto.base.data.network.NetworkFactory
import com.toybeth.dokto.stripepayment.BuildConfig
import com.toybeth.dokto.stripepayment.data.StripeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.StringKey
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