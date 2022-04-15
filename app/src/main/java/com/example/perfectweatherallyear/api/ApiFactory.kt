package com.example.perfectweatherallyear.api

import android.util.Log
import com.example.perfectweatherallyear.BuildConfig
import com.example.perfectweatherallyear.repository.remote.forecast.ForecastApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val KEY_QUERY_PARAM = "key"
object ApiFactory {

    fun createWeatherApi(): ForecastApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.base_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ForecastApi::class.java)
    }

    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_QUERY_PARAM, BuildConfig.API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("RESTLog", message)
        }
    }).apply { level = HttpLoggingInterceptor.Level.BODY  }
}