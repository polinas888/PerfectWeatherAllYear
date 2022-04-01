package com.example.perfectweatherallyear.di

import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherFragment
import dagger.Component

@Component(modules = [ModuleViewModelFactory::class, ModuleService:: class, ApiRepositoryFactory:: class])
interface AppComponent {
    fun inject(fragment: WeekWeatherFragment)
}