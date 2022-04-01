package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object ModuleViewModelFactory {
    @Provides
    fun provideWeekWeatherViewModelFactory(weatherRepository: WeatherRepository): WeekWeatherViewModelFactory {
        return WeekWeatherViewModelFactory(repository = weatherRepository)
    }
}