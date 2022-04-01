package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.api.ApiFactory
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideWeatherService(): WeatherApiCom {
        return ApiFactory.weatherApiRetrofit().create(WeatherApiCom::class.java)
    }
}