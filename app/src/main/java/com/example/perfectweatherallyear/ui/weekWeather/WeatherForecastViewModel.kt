package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.util.wrapEspressoIdlingResource
import kotlinx.coroutines.launch

const val DAYS_NUMBER = 3

class WeatherForecastViewModel(
    val weatherRepository: WeatherRepository,
    val locationRepository: LocationRepository
) : ViewModel() {
    val weatherForecastLiveData = MutableLiveData<List<DayWeather>>()

    fun loadForecast(location: Location) {
        wrapEspressoIdlingResource {
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
    }

    private suspend fun getWeatherForecast(location: Location, daysAmount: Int): DataResult<List<DayWeather>> {
        wrapEspressoIdlingResource {
            return weatherRepository.getWeatherForecast(location, daysAmount)
        }
    }
}

