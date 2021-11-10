package com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Weather
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val location: Any,
    val current: Any,
    val forecast: Forecast
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastDay: List<DayItem>
)

data class DayItem(
    val date: String,
    val day: Day
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxTemp: String,
    @SerializedName("mintemp_c")
    val minTemp: String,
    @SerializedName("daily_chance_of_rain")
    val precipitation: String,
    @SerializedName("maxwind_kph")
    val wind: String
)

fun ForecastResponse.convertToDayWeather(): List<DayWeather> {
    val forecastDay = forecast.forecastDay
    val dayWeatherList = mutableListOf<DayWeather>()
    forecastDay.forEach {
        val weather = Weather(it.day.minTemp, it.day.maxTemp, it.day.precipitation, it.day.wind)
        val dayWeather = DayWeather(it.date, weather)
        dayWeatherList.add(dayWeather)
    }
    return dayWeatherList
}