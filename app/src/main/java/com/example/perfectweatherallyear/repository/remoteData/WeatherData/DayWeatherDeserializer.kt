package com.example.perfectweatherallyear.repository.remoteData.WeatherData

import com.example.perfectweatherallyear.model.DayWeather
import com.google.gson.*
import java.lang.reflect.Type

class DayWeatherDeserializer : JsonDeserializer<List<DayWeather>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<DayWeather> {
        val weekWeather = mutableListOf<DayWeather>()

        val jsonObject: JsonObject? = json?.let { json.asJsonObject }
        val weekWeatherJsonArray: JsonArray = jsonObject?.get("data")?.asJsonArray ?: JsonArray()

            for ((index, dayWeatherJsonElement: JsonElement) in weekWeatherJsonArray.withIndex()) {
                val weekWeatherJsonObject: JsonObject = dayWeatherJsonElement.asJsonObject
                val forecastDayJsonObject = weekWeatherJsonObject.get("forecast").asJsonObject.get("forecastday").asJsonArray
                val dayJsonObject = forecastDayJsonObject[index].asJsonObject.get("day").asJsonObject
                val temperatureMin: String = dayJsonObject.get("maxtemp_c").toString()
                val temperatureMax: String = dayJsonObject.get("mintemp_c").asString
                val precipitation: String = dayJsonObject.get("daily_chance_of_rain").asString
                val wind: String = dayJsonObject.get("maxwind_kph").asString
                val date: String = dayJsonObject.get("date").asString

                weekWeather.add(DayWeather(temperatureMin, temperatureMax, precipitation, wind, date))
            }

        return weekWeather
    }
}
