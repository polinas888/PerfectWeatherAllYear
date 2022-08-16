package com.example.perfectweatherallyear.ui.detailWeather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import javax.inject.Inject

class DetailWeatherViewModelFactory @Inject constructor(
    val weatherRepository: WeatherRepository, val locationRepository: LocationRepository, val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailWeatherViewModel::class.java))
                return DetailWeatherViewModel(weatherRepository, locationRepository, context) as T
            else
                throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
