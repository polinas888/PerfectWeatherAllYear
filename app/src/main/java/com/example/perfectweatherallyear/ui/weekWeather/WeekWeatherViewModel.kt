package com.example.perfectweatherallyear.ui.weekWeather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.SuccessResult
import com.example.perfectweatherallyear.repository.remote.ForecastRepository
import kotlinx.coroutines.launch

class WeekWeatherViewModel : ViewModel() {
    private val forecastRepository: ForecastRepository = ForecastRepository()
    val weekWeatherLiveData = MutableLiveData<List<DayWeather>>()

    fun fetchForecast(city: String, days: String) {
        viewModelScope.launch {
            forecastRepository.fetchForecast(city, days).let {
                when (it) {
                    is SuccessResult -> {
                        weekWeatherLiveData.value = it.data!!
                    }
                    else -> {
                        Log.i("No data", "No data")
                    }
                }
            }
        }
    }
}
