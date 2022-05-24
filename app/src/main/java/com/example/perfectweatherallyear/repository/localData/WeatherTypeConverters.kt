package com.example.perfectweatherallyear.repository.localData

import androidx.room.TypeConverter
import com.example.perfectweatherallyear.model.GeneralWeather
import com.google.gson.Gson
import java.util.*

class WeatherTypeConverters {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun appToString(generalWeather: GeneralWeather): String = Gson().toJson(generalWeather)

    @TypeConverter
    fun stringToApp(string: String): GeneralWeather = Gson().fromJson(string, GeneralWeather::class.java)

}
