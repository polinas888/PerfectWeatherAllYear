package com.example.perfectweatherallyear.repository.localData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDayWeather(dataWeatherData: List<DayWeather>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>)

    @Query("SELECT * FROM dayweather WHERE cityId = :city AND date = :date")
    suspend fun getDayWeatherByCityAndDate(city: String, date: String): DayWeather

    @Query("SELECT * FROM dayweather JOIN location ON location.id = dayweather.cityId WHERE dayweather.cityId = :cityId LIMIT :daysAmount")
    suspend fun getWeatherForecast(cityId: String, daysAmount: Int): List<DayWeather>

    @Query("SELECT * FROM hourweather JOIN dayweather ON id = dayWeatherId")
    suspend fun getHourlyWeather(): List<HourWeather>
}