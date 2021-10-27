package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather

class DetailWeatherViewModel : ViewModel() {
    var dayWeather: DayWeather? = null

    val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String> = _temperature
    val _precipitation = MutableLiveData<String>()
    val precipitation: LiveData<String> = _precipitation
    val _wind = MutableLiveData<String>()
    val wind: LiveData<String> = _wind

    fun load() {
        val dayWeather = getDayWeatherData()
        _temperature.value = dayWeather?.temperature
        _precipitation.value = dayWeather?.precipitation.toString()
        _wind.value = dayWeather?.wind.toString()
    }

    fun setDayWeatherData(dayWeather: DayWeather) {
        this.dayWeather = dayWeather
    }

    private fun getDayWeatherData(): DayWeather? {
        return dayWeather
    }
}