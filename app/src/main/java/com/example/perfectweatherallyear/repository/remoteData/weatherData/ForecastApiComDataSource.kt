package com.example.perfectweatherallyear.repository.remoteData.weatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.convertToDayWeather
import retrofit2.Retrofit
import javax.inject.Inject

class ForecastApiComDataSource @Inject constructor(retrofit: Retrofit) : RemoteWeatherDataSource() {

    private val remoteService: WeatherApiCom by lazy {
        retrofit.create(WeatherApiCom::class.java)
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