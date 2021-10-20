package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather

class WeekWeatherViewModel: ViewModel() {
    val weekWeatherMap = mapOf(
        "Monday" to DayWeather("+15/+5", 15, 5),
        "Tuesday" to DayWeather("+15/+5", 15, 5), "Wednesday" to DayWeather("+16/+6", 20, 10),
        "Thursday" to DayWeather("+20/+15", 70, 2), "Friday" to DayWeather("+15/+7", 10, 15),
        "Saturday" to DayWeather("+10/+5", 20, 5), "Sunday" to DayWeather("+7/+0", 15, 8)
    )

    fun getCurrentDayWeather(position: Int): DayWeather? {
        return weekWeatherMap[weekWeatherMap.keys.toTypedArray()[position]]
    }
}