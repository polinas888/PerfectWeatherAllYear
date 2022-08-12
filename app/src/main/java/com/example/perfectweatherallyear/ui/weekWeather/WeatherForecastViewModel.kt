package com.example.perfectweatherallyear.ui.weekWeather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.repository.WeatherRepository

const val DAYS_NUMBER = 3

class WeatherForecastViewModel(
    val weatherRepository: WeatherRepository,
    val locationRepository: LocationRepository,
    val context: Context
) : ViewModel() {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)
    var remoteWeatherForecastLiveData = MutableLiveData<List<DayWeather>>()

    fun loadForecast(location: Location) {
        if (mConnectionDetector.isConnectingToInternet()) {
            try {
                weatherRepository.updateForecastWeather(location, DAYS_NUMBER)
                remoteWeatherForecastLiveData = weatherRepository.getRemoteWeatherForecastLiveData()
                getLocalWeatherForecast(location, DAYS_NUMBER)
            } catch (e: Exception) {
                Log.i("WeatherLog", "Couldn't get online weather")
            }
        } else {
            getLocalWeatherForecast(location, DAYS_NUMBER)
        }
    }

    fun getLocalWeatherForecast(location: Location, numDays: Int) : MutableLiveData<List<DayWeather>> {
        return weatherRepository.getLocalWeatherForecastLiveData(location, numDays)
    }

    fun clear() {
        weatherRepository.clear()
    }
}