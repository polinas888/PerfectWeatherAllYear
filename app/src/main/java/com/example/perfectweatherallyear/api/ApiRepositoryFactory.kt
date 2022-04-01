package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.repository.WeatherRepositoryImp
import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import dagger.Module
import dagger.Provides

@Module
object ApiRepositoryFactory {

    @Provides
    fun provideWeatherRepository(remoteWeatherDataSource: RemoteWeatherDataSource): WeatherRepository {
        return WeatherRepositoryImp(remoteDataSource = remoteWeatherDataSource)
    }

    @Provides
    fun provideRemoteWeatherDataSource(weatherApiCom: WeatherApiCom) : RemoteWeatherDataSource {
        return ForecastApiComDataSource(weatherApiCom)
    }
}
