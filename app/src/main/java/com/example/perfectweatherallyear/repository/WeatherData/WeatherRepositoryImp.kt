package com.example.perfectweatherallyear.repository.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.Result

class WeatherRepositoryImp: WeatherRepository {

    override suspend fun getDayWeather(weekDay: String): Result<List<DayWeather>> {
        return try {
            val data = listOf(DayWeather("+7/0", 30, 5))
            Result.Ok(data)
        } catch (ex: Exception) {
            Result.Error(ex.message)
        }
    }

    override suspend fun getWeekWeather(): Result<List<DayWeather>> {
        return try {
            val data = listOf(DayWeather("+15/+5", 15, 5),
                DayWeather("+15/+5", 15, 5),
                DayWeather("+16/+6", 20, 10),
                DayWeather("+20/+15", 70, 2),
                DayWeather("+15/+7", 10, 15),
                DayWeather("+10/+5", 20, 5),
                DayWeather("+7/+0", 15, 8))
            Result.Ok(data)
        } catch (ex: java.lang.Exception) {
            Result.Error(ex.message)
        }
    }
}

