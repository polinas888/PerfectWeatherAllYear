package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather

class DetailWeatherViewModel : ViewModel() {
    var dayWeather: DayWeather? = null

    val temperatureMax = MutableLiveData<String>()
    val temperatureMin = MutableLiveData<String>()
    val precipitation = MutableLiveData<String>()
    val wind = MutableLiveData<String>()

    fun load() {
        getDayWeatherData()?.also {
            temperatureMax.value = dayWeather?.max_temperature.toString()
            temperatureMin.value = dayWeather?.min_temperature.toString()
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