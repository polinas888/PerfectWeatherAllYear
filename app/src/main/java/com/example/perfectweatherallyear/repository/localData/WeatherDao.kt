package com.example.perfectweatherallyear.repository.localData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayWeather(dataWeatherData: List<DayWeather>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>)

    @Query("SELECT * FROM dayweather WHERE cityId = :city AND date = :date")
    suspend fun getDayWeatherByCityAndDate(city: Int, date: String): DayWeather

    @Query("SELECT * FROM dayweather JOIN location ON location.id = dayweather.cityId WHERE dayweather.cityId = :cityId LIMIT :daysAmount")
    suspend fun getWeatherForecast(cityId: Int, daysAmount: Int): List<DayWeather>

    @Query("SELECT * FROM hourweather JOIN dayweather ON dayweather.id = hourweather.dayWeatherId")
    suspend fun getHourlyWeather(): List<HourWeather>
}