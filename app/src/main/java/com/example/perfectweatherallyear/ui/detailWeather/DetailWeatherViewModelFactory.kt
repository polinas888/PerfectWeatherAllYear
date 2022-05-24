package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfectweatherallyear.repository.WeatherRepository
import javax.inject.Inject

class DetailWeatherViewModelFactory @Inject constructor(val repository: WeatherRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailWeatherViewModel::class.java))
                return DetailWeatherViewModel(repository) as T
            else
                throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
