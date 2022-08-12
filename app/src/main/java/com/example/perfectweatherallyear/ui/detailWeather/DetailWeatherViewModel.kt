package com.example.perfectweatherallyear.ui.detailWeather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.api.ConnectionDetector
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.WeatherRepository

class DetailWeatherViewModel(val weatherRepository: WeatherRepository, context: Context) : ViewModel() {
    private val mConnectionDetector: ConnectionDetector = ConnectionDetector(context)
    var remoteHourWeatherLiveData = MutableLiveData<List<HourWeather>>()

    fun loadHourlyWeather(dayWeather: DayWeather) {
        if (mConnectionDetector.isConnectingToInternet()) {
            try {
                getUpdatedRemoteHourWeather(dayWeather)
                getLocalHourWeatherLiveData(dayWeather)
            } catch (e: Exception) {
                Log.i("WeatherLog", "Couldn't get online weather")
            }
        } else {
            getLocalHourWeatherLiveData(dayWeather)
        }
    }

    fun getLocalHourWeatherLiveData(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>> {
        return weatherRepository.getLocalHourWeatherLiveData(dayWeather)
    }

    fun getUpdatedRemoteHourWeather(dayWeather: DayWeather) : MutableLiveData<List<HourWeather>> {
        return weatherRepository.getUpdatedRemoteHourWeather(dayWeather)
    }

    fun clear() {
        weatherRepository.clear()
    }
}