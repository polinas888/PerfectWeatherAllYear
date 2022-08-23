package com.example.perfectweatherallyear.api

import com.example.perfectweatherallyear.BuildConfig
import com.example.perfectweatherallyear.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRetrofitFactory {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Logger.i(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private val httpClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(provideHttpLoggingInterceptor())
        .build()

    fun weatherApiRetrofit(): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BuildConfig.base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
