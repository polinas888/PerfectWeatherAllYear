package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.*
import java.lang.reflect.Type

class DayWeatherDeserializer : JsonDeserializer<List<DayWeather>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<DayWeather> {
        val weekWeather = mutableListOf<DayWeather>()

        val jsonObject: JsonObject? = json?.let { json.asJsonObject }
        val success: Boolean = jsonObject?.get("success")?.asBoolean ?: false
        val weekWeatherJsonArray: JsonArray = jsonObject?.get("data")?.asJsonArray ?: JsonArray()

            for ((index, dayWeatherJsonElement: JsonElement) in weekWeatherJsonArray.withIndex()) {
                val dayWeatherJsonObject: JsonObject = dayWeatherJsonElement.asJsonObject
//                val day: Day = Day()
//                val forecastday: Forecastday = Forecastday(day)
//                val forecast: Forecast = Forecast(listOf(forecastday))
//                val weatherData: WeatherData = WeatherData(forecast)

                val forecastDay = dayWeatherJsonObject.get("forecast").asJsonObject.get("forecastday").asJsonArray
                val day = forecastDay[index]

                val temperatureMin: String = dayWeatherJsonObject.get("forecast.forecastday[index]" +
                        ".day.maxtemp_c").asString
                val temperatureMax: String = dayWeatherJsonObject.get("forecast.forecastday.day.mintemp_c").asString
                val precipitation: Int = dayWeatherJsonObject.get("forecast.forecastday.day.daily_chance_of_rain").asInt
                val wind: Int = dayWeatherJsonObject.get("forecast.forecastday.day.maxwind_kph").asInt

                weekWeather.add(DayWeather(temperatureMin, temperatureMax, precipitation, wind))
            }

        return weekWeather
    }
}
