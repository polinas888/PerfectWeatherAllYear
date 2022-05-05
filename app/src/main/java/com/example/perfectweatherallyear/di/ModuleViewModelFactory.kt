package com.example.perfectweatherallyear.di

import android.content.Context
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.detailWeather.DetailWeatherViewModelFactory
import com.example.perfectweatherallyear.ui.location.LocationViewModelFactory
import com.example.perfectweatherallyear.ui.weekWeather.WeatherForecastViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object ModuleViewModelFactory {
    @Provides
    fun provideForecastViewModelFactory(weatherRepository: WeatherRepository, locationRepository: LocationRepository, context: Context): WeatherForecastViewModelFactory {
        return WeatherForecastViewModelFactory(weatherRepository = weatherRepository,
            locationRepository = locationRepository, context = context)
    }

    @Provides
    fun provideLocationViewModelFactory(locationRepository: LocationRepository): LocationViewModelFactory {
        return LocationViewModelFactory(repository = locationRepository)
    }

    @Provides
    fun provideDetailViewModelFactory(weatherRepository: WeatherRepository): DetailWeatherViewModelFactory {
        return DetailWeatherViewModelFactory(repository = weatherRepository)
    }
}