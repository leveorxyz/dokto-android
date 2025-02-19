package com.toybethsystems.dokto.base.data.network

import android.content.Context
import com.orhanobut.logger.Logger
import com.toybethsystems.dokto.base.BuildConfig
import com.toybethsystems.dokto.base.data.preference.AppPreference
import com.toybethsystems.dokto.base.data.preference.AppPreferenceImpl
import com.toybethsystems.dokto.base.utils.ConnectivityAndInternetAccess
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkFactory {

    private const val BASE_URL = BuildConfig.BASE_URL
    private const val TIME_OUT = 60L

    fun <Service> createService(appContext: Context, serviceClass: Class<Service>, baseUrl: String = BASE_URL, token: String = ""): Service {
        return getRetrofit(
            appContext,
            baseUrl,
            getOkHttpClient(
                getAuthInterceptor(
                    appContext,
                    token
                ),
                getLogInterceptors()
            )
        ).create(serviceClass)
    }

    fun <Service> createServiceForCoroutine(appContext: Context, serviceClass: Class<Service>, baseUrl: String = BASE_URL, token: String = ""): Service {
        return getRetrofitForCoroutine(
            appContext,
            baseUrl,
            getOkHttpClient(
                getAuthInterceptor(
                    appContext,
                    token
                ),
                getLogInterceptors()
            )
        ).create(serviceClass)
    }

    fun getRetrofit(appContext: Context, baseUrl: String = "", token: String = ""): Retrofit {
        return getRetrofit(
            context = appContext,
            baseUrl = baseUrl,
            okHttpClient = getOkHttpClient(
                getAuthInterceptor(
                    appContext,
                    token
                ),
                getLogInterceptors()
            )
        )
    }

    fun getRetrofit(
        context: Context,
        baseUrl: String = BASE_URL,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(
                RxErrorHandlingCallAdapterFactory.create(
                    context
                )
            )
            .client(okHttpClient)
            .callbackExecutor {
                Logger.d("returning")
            }
            .build()
    }

    fun getRetrofitForCoroutine(
        context: Context,
        baseUrl: String = BASE_URL,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .callbackExecutor {
                Logger.d("returning")
            }
            .build()
    }

    fun getOkHttpClient(authInterceptor: Interceptor, logInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(
                TIME_OUT,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                TIME_OUT,
                TimeUnit.SECONDS
            )
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(logInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request {
                    return response.request
                }

            })
            // .cache(cache)
            .build()
    }

    fun getAuthInterceptor(appContext: Context, token: String = ""): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request().newBuilder()
//            val token = getSharedPreference(appContext).token
                if (token.isNotEmpty()) {
                    requestBuilder.addHeader("Authorization", token)
                        .addHeader("Cache-control", "no-cache")
                }
                try {
                    return chain.proceed(requestBuilder.build())
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (ConnectivityAndInternetAccess.isConnectedToInternet(
                            appContext,
                            chain.request().url.host
                        )
                    )
                        throw e
                    else
                        throw Exception("Slow or no internet connection")
                }

            }

        }
    }

    fun getLogInterceptors(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        }
    }


    private fun getCache(context: Context): Cache {
        val cacheSize: Long = (10 * 1024 * 1024).toLong() // 10 MB
        return Cache(context.applicationContext.cacheDir, cacheSize)
    }

    private fun getSharedPreference(appContext: Context): AppPreference {
        return AppPreferenceImpl(appContext)
    }
}