package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.ApiFactory.weatherApiRetrofit
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model.ForecastResponse

class RemoteDataSource {

    private val remoteService: WeatherRepositoryImp by lazy {
        weatherApiRetrofit(DayWeather::class.java, DayWeatherDeserializer())
            .create(WeatherRepositoryImp::class.java)
    }

    suspend fun getDayWeather(city: String, data: Int): List<DayWeather> = remoteService.getDayWeather(city, data)

    suspend fun getWeekWeather(city: String, daysAmount: Int): List<DayWeather> {
        val remoteResponse: ForecastResponse = remoteService.getWeekWeather(city, daysAmount)
        val result = mutableListOf<DayWeather>()
        while (result.size<7) {
            result.add(DayWeather("-1", "+2", 8, 9))
        }
        return result
    }
}