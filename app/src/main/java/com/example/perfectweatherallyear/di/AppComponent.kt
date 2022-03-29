package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherFragment
import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherViewModelFactory
import dagger.Component
import retrofit2.Retrofit

@Component(modules = [ModuleViewModelFactory::class, ModuleRetrofit:: class])
interface AppComponent {
    fun inject(fragment: WeekWeatherFragment)
    fun inject(forecastApiComDataSource: ForecastApiComDataSource)
    val weekWeatherViewModelFactory: WeekWeatherViewModelFactory
    val retrofit: Retrofit
}