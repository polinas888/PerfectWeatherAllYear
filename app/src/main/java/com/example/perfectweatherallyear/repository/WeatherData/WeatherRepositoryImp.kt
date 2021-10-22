package com.example.perfectweatherallyear.repository.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.WeekDay

class WeatherRepositoryImp: WeatherRepository {
    override suspend fun getDayWeather(weekDay: WeekDay): List<DayWeather> {
        return listOf(DayWeather("+7/0", 30, 5))
    }

    override suspend fun getWeekWeather(): List<DayWeather> {
        return listOf(DayWeather("+15/+5", 15, 5),
            DayWeather("+15/+5", 15, 5),
            DayWeather("+16/+6", 20, 10),
            DayWeather("+20/+15", 70, 2),
            DayWeather("+15/+7", 10, 15),
            DayWeather("+10/+5", 20, 5),
            DayWeather("+7/+0", 15, 8))
    }
}

