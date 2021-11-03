package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.ApiFactory.weatherApiRetrofit
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather

class RemoteDataSource {

    private val remoteService: WeatherRepositoryImp by lazy {
        weatherApiRetrofit().create(WeatherRepositoryImp::class.java)
    }

    suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
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