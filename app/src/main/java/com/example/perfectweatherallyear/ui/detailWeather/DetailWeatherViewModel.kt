package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather

class DetailWeatherViewModel : ViewModel() {
    var weather: DayWeather? = null

    val minTemperature = MutableLiveData<String>()
    val maxTemperature = MutableLiveData<String>()
    val precipitation = MutableLiveData<String>()
    val wind = MutableLiveData<String>()

    fun load() {
        getDayWeatherData()?.also {
            minTemperature.value = weather?.weather?.temperatureMin
            maxTemperature.value = weather?.weather?.temperatureMax
            precipitation.value = weather?.weather?.precipitation.toString()
            wind.value = weather?.weather?.wind.toString()
        }
    }

    fun setDayWeatherData(weather: DayWeather) {
        this.weather = weather
    }

    private fun getDayWeatherData(): DayWeather? {
        return weather
    }
}