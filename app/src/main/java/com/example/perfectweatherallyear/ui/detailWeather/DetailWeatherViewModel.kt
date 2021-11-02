package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.Repository

class DetailWeatherViewModel (val repository: Repository) : ViewModel() {
    var dayWeather: DayWeather? = null

    val temperature = MutableLiveData<String>()
    val precipitation = MutableLiveData<String>()
    val wind = MutableLiveData<String>()

    fun load() {
        getDayWeatherData()?.also {
            temperature.value = dayWeather?.temperatureMin
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