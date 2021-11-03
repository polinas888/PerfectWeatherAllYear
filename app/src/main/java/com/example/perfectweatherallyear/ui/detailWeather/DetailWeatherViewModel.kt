package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather

class DetailWeatherViewModel : ViewModel() {
    var dayWeather: DayWeather? = null

    val minTemperature = MutableLiveData<String>()
    val maxTemperature = MutableLiveData<String>()
    val precipitation = MutableLiveData<String>()
    val wind = MutableLiveData<String>()

    fun load() {
        getDayWeatherData()?.also {
            minTemperature.value = dayWeather?.temperatureMin
            maxTemperature.value = dayWeather?.temperatureMax
            precipitation.value = dayWeather?.precipitation.toString()
            wind.value = dayWeather?.wind.toString()
        }
    }

    fun setDayWeatherData(dayWeather: DayWeather) {
        this.dayWeather = dayWeather
    }

    private fun getDayWeatherData(): DayWeather? {
        return dayWeather
    }
}