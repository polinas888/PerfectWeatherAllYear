package com.example.perfectweatherallyear.di

import android.content.Context
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherFragment
import com.example.perfectweatherallyear.ui.location.LocationFragment
import com.example.perfectweatherallyear.ui.weekWeather.WeatherForecastFragment
import com.example.perfectweatherallyear.util.WorkerHandler
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ModuleViewModelFactory::class, ModuleService:: class, ApiRepositoryFactory:: class, ModuleDatabase:: class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun inject(fragment: LocationFragment)
    fun inject(fragment: WeatherForecastFragment)
    fun inject(fragment: DetailWeatherFragment)
    fun inject(workerHandler: WorkerHandler)
}