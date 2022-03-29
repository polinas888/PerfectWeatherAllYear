package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfectweatherallyear.repository.WeatherRepository

class WeekWeatherViewModelFactory(val repository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeekWeatherViewModel::class.java))
            return WeekWeatherViewModel(repository) as T
        else
            throw IllegalArgumentException("Unable to construct viewModel")
    }
}