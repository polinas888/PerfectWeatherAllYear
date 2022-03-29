package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ModuleRetrofit {
    @Provides
    fun provideWeatherApiRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BuildConfig.WEATHER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    fun provideHttpClient(authInterceptor: Interceptor) = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    fun provideAuthInterceptor() = Interceptor { chain ->
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

    @Provides
    fun provideLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
