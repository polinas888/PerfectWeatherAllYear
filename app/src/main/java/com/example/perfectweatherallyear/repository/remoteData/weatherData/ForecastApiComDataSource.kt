package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.api.ApiFactory.weatherApiRetrofit
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather

class ForecastApiComDataSource : RemoteWeatherDataSource() {

    private val remoteService: WeatherApiCom by lazy {
        weatherApiRetrofit().create(WeatherApiCom::class.java)
    }

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