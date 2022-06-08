package com.example.perfectweatherallyear.repository.remoteData.weatherapicom.model

import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Any,
    @SerializedName("forecast")
    val forecast: Forecast
)

data class Location(
    @SerializedName("name")
    val city: String
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastDay: List<DayItem>
)

data class DayItem(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: Day,
    @SerializedName("hour")
    val hourWeather: List<Hour>
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

data class Hour(
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_c")
    val temp: String,
    @SerializedName("precip_mm")
    val precipitation: String,
    @SerializedName("wind_kph")
    val wind: String
)

fun ForecastResponse.convertToDayWeather(cityId: Int): List<DayWeather> {
    val forecastDay = forecast.forecastDay
    val dayWeatherList = mutableListOf<DayWeather>()
    forecastDay.forEach {
        val weather = GeneralWeather(it.day.minTemp, it.day.maxTemp, it.day.precipitation, it.day.wind)
        val dayWeather = DayWeather(date = it.date, cityId = cityId, generalWeather = weather)
        dayWeatherList.add(dayWeather)
    }
    return dayWeatherList
}

//fun ForecastResponse.convertToHourWeather(dayWeather: DayWeather): Observable<List<HourWeather>> {
//    val forecastDay = forecast.forecastDay
//    val hourWeatherList = mutableListOf<HourWeather>()
//    val dayItem = getDayItemFromListItems(forecastDay, dayWeather.date)
//    dayItem?.hourWeather?.forEach {
//            val hourWeather = HourWeather(time = it.time, temperature = it.temp, precipitation = it.precipitation, wind = it.wind, dayWeatherId = dayWeather.id)
//        hourWeatherList.add(hourWeather)
//    }
//    return hourWeatherList
//}

private fun getDayItemFromListItems(listDayItem: List<DayItem>, date: String): DayItem? {
    listDayItem.forEach {
        if (it.date == date)
            return it
    }
    return null
}