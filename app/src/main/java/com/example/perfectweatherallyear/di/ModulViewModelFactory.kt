package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.repository.WeatherRepositoryImp
import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object ModuleViewModelFactory {

    @Provides
    fun provideWeekWeatherViewModelFactory(weatherRepository: WeatherRepository): WeekWeatherViewModelFactory {
        return WeekWeatherViewModelFactory(repository = weatherRepository)
    }

    @Provides
    fun provideWeatherRepository(remoteWeatherDataSource: RemoteWeatherDataSource): WeatherRepository {
        return WeatherRepositoryImp(remoteDataSource = remoteWeatherDataSource)
    }

    @Provides
    fun provideRemoteWeatherDataSource() : RemoteWeatherDataSource {
        return ForecastApiComDataSource()
    }
}
