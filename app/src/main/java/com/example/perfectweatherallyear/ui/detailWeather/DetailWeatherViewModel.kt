package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather

class DetailWeatherViewModel: ViewModel() {
    var dayWeather: DayWeather? = null

    fun setDayWeatherData(dayWeather: DayWeather?) {
        this.dayWeather = dayWeather
    }

    fun getDayWeatherData(): DayWeather? {
        return dayWeather
    }
}