package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import javax.inject.Inject

class WeatherForecastViewModelFactory @Inject constructor(val weatherRepository: WeatherRepository, val locationRepository: LocationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java))
            return WeatherForecastViewModel(weatherRepository, locationRepository) as T
        else
            throw IllegalArgumentException("Unable to construct viewModel")
    }
}