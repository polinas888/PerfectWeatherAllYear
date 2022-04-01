package com.example.perfectweatherallyear.api

import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiRepositoryFactory {
    @Provides
    fun provideRemoteWeatherDataSource(weatherApiCom: WeatherApiCom): RemoteWeatherDataSource {
        return ForecastApiComDataSource(weatherApiCom)
    }
}