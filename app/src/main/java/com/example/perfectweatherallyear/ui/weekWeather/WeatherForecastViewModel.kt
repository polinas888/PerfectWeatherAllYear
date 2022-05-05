package com.example.perfectweatherallyear.ui.weekWeather

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import kotlinx.coroutines.launch

const val DAYS_NUMBER = 7

class WeatherForecastViewModel(
    val weatherRepository: WeatherRepository,
    val locationRepository: LocationRepository,
    val context: Context
) : ViewModel() {
    val weatherForecastLiveData = MutableLiveData<List<DayWeather>>()

    fun loadData(location: Location) {
        viewModelScope.launch {
            when (val weatherForecast = getWeatherForecast(location, DAYS_NUMBER)) {
                is DataResult.Ok -> {
                    weatherForecastLiveData.value = weatherForecast.response!!
                }
                is DataResult.Error ->
                    weatherForecast.error
            }
        }
    }

    private suspend fun getWeatherForecast(
        location: Location,
        daysAmount: Int
    ): DataResult<List<DayWeather>> {
        return weatherRepository.getWeatherForecast(location, daysAmount)
    }
}

