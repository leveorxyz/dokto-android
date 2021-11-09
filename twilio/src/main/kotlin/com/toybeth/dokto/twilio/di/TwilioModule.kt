package com.toybeth.dokto.twilio.di

import android.content.Context
import android.content.SharedPreferences
import com.toybeth.dokto.base.data.network.NetworkFactory
import com.toybeth.dokto.twilio.BuildConfig
import com.toybeth.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybeth.dokto.twilio.data.rest.TwilioAuthDataSource
import com.toybeth.dokto.twilio.data.rest.TwilioRestApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TwilioModule {
    @Provides
    @Singleton
    fun provideTwilioNetworkService(@ApplicationContext context: Context): TwilioRestApiDataSource {
        return com.toybeth.dokto.base.data.network.NetworkFactory.createServiceForCoroutine(
            context,
            TwilioRestApiDataSource::class.java
        )
    }

    @Provides
    fun providesTokenService(
        authService: TwilioRestApiDataSource,
        sharedPreferences: TwilioSharedPreference
    ): TwilioAuthDataSource {
        return TwilioAuthDataSource(authService, sharedPreferences)
    }
}