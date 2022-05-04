package com.example.perfectweatherallyear.ui.weekWeather

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

class WeatherForecastViewModel(val weatherRepository: WeatherRepository, val locationRepository: LocationRepository ) : ViewModel() {
    val weatherForecastLiveData = MutableLiveData<List<DayWeather>>()

    fun loadData(location: Location) {
        viewModelScope.launch {
            when (val weatherForecast = getWeatherForecast(location.name, DAYS_NUMBER)) {
                is DataResult.Ok -> {
                    weatherForecastLiveData.value = weatherForecast.response!!
                }
                is DataResult.Error -> weatherForecast.error
            }
        }
    }

    private suspend fun getWeatherForecast(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        val cityId = locationRepository.getLocationIdByCityName(city)
        return weatherRepository.getWeatherForecast(city, daysAmount, cityId)
    }
}

