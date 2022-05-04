package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteWeatherDataSource,
    private val localWeatherDataSource: LocalWeatherDataSource
) : WeatherRepository {

    override suspend fun getWeatherForecast(city: String, daysAmount: Int, cityId: String): DataResult<List<DayWeather>> {
        return try {
            val remoteWeekWeather = remoteDataSource.getWeatherForecast(city, daysAmount)
            localWeatherDataSource.insertDayWeather(remoteWeekWeather)
            val localWeekWeather = localWeatherDataSource.getWeatherForecast(cityId, daysAmount)
            DataResult.Ok(localWeekWeather)
        } catch (e: Exception) {
            DataResult.Error(e.message.toString())
        }
    }

    override suspend fun getHourlyWeather(daysAmount: Int, cityId: String, date: String
    ): DataResult<List<HourWeather>> {
        return try {
            val hourlyWeather = remoteDataSource.getHourlyWeather(daysAmount, cityId, date)
            localWeatherDataSource.insertHourlyWeather(hourlyWeather)
            val localHourWeather = localWeatherDataSource.getHourlyWeather()
            DataResult.Ok(localHourWeather)
        } catch (e: Exception) {
            DataResult.Error(e.message.toString())
        }
    }
}
