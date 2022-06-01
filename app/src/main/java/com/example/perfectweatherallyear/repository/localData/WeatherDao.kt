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
    fun insertDayWeather(dataWeatherData: List<DayWeather>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>)

    @Query("SELECT * FROM dayweather WHERE cityId = :city AND date = :date")
    suspend fun getDayWeatherByCityAndDate(city: Int, date: String): DayWeather

    @Query("SELECT * FROM dayweather WHERE dayweather.cityId = :cityId ORDER BY ID DESC LIMIT :daysAmount")
    fun getWeatherForecast(cityId: Int, daysAmount: Int): List<DayWeather>

    @Query("SELECT * FROM hourweather WHERE hourweather.dayWeatherId = :dayWeatherId")
    suspend fun getHourlyWeather(dayWeatherId: Int): List<HourWeather>
}