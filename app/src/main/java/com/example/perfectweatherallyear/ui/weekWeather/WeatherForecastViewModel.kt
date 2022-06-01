package com.example.perfectweatherallyear.ui.weekWeather

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository

const val DAYS_NUMBER = 3

class WeatherForecastViewModel(
    val weatherRepository: WeatherRepository,
    val locationRepository: LocationRepository
) : ViewModel() {
    val weatherForecastLiveData = MutableLiveData<List<DayWeather>>()
    private val handler = android.os.Handler(Looper.getMainLooper())

    fun loadForecast(location: Location) {
            when (val weatherForecast = getWeatherForecast(location, DAYS_NUMBER)) {
                is DataResult.Ok -> {
                    handler.post {
                        weatherForecastLiveData.value = weatherForecast.response!!
                    }
                }
                is DataResult.Error ->
                    weatherForecast.error
            }
    }

    private fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        return weatherRepository.getWeatherForecast(location, daysAmount)
    }
}

