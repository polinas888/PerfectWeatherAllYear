package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.example.perfectweatherallyear.ui.location.LocationFragment
import com.example.perfectweatherallyear.ui.weekWeather.WeatherForecastFragment
import dagger.Component

@Component(modules = [ModuleViewModelFactory::class, ModuleService:: class, ApiRepositoryFactory:: class])
interface AppComponent {
    fun inject(fragment: LocationFragment)
    fun inject(fragment: WeatherForecastFragment)
    fun inject(fragment: DetailWeatherFragment)
}