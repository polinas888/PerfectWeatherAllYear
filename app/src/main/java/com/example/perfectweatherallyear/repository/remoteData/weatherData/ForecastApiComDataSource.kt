package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import javax.inject.Inject

class ForecastApiComDataSource @Inject constructor(val remoteService: WeatherApiCom) : RemoteWeatherDataSource() {

    override suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        val response = remoteService.getWeekWeather(city, daysAmount)

        val result = when(response.isSuccessful) {
            true -> {
                DataResult.Ok(response.body()!!.convertToDayWeather())
            }
            else -> {
                DataResult.Error(response.errorBody().toString())
            }
        }
        return result
    }
}