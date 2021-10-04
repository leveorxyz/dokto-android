package com.toybeth.docto

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.toybeth.docto.data.ApiService
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class NetworkServiceTest {

    private var mockWebServer = MockWebServer()
    private lateinit var apiService: ApiService

    @Before
    fun setup() {

        val dispatcher = object: Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                print(request.path)
                return when(request.path) {
                    "/resources/docto.json" -> {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody("{\"message\": \"Docto\"}")
                    }
                    else -> {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody("{\"message\": \"Docto!!!!!\"}")
                    }
                }
            }

        }
        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()
        val context = ApplicationProvider.getApplicationContext<Context>()
        apiService = com.toybeth.docto.core.data.network.NetworkFactory.getRetrofit(context,
            mockWebServer.url("/").toUri().toString(),
            com.toybeth.docto.core.data.network.NetworkFactory.getOkHttpClient(com.toybeth.docto.core.data.network.NetworkFactory.getAuthInterceptor(context), com.toybeth.docto.core.data.network.NetworkFactory.getLogInterceptors()))
            .create(ApiService::class.java)
    }

    @After
    fun closeServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun testMessageResponse() {
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"message\": \"Docto\"}")
//        mockWebServer.enqueue(response)
//        val message = apiService.getMessage().blockingGet()
//        assert(message.body()?.message == "Docto")
    }
}