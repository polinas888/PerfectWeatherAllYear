package com.example.perfectweatherallyear

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object ApiFactory {

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

    val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val httpClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .build()

    private fun createGsonConverter(type: Type, typeAdapter: Any): Converter.Factory? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(type, typeAdapter)
        val gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }

    fun weatherApiRetrofit(type: Type, typeAdapter: Any): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(createGsonConverter(type, typeAdapter))
        .build()
}
