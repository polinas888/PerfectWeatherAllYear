package com.example.perfectweatherallyear.repository.localData

import android.database.sqlite.SQLiteConstraintException
import androidx.room.*
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDayWeather(dataWeatherData: List<DayWeather>)

    @Update
    suspend fun updateDayWeather(dataWeatherData: List<DayWeather>)

    @Transaction
    suspend fun upsertForecast(dataWeatherData: List<DayWeather>) {
        try {
            insertDayWeather(dataWeatherData)
        }
        catch (exception: SQLiteConstraintException) {
            updateDayWeather(dataWeatherData)
        }
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertHourlyWeather(dataWeatherData: List<HourWeather>)

    @Update
    suspend fun updateHourlyWeather(dataWeatherData: List<HourWeather>)

    @Transaction
    suspend fun upsertHourlyWeather(dataWeatherData: List<HourWeather>) {
        try {
            insertHourlyWeather(dataWeatherData)
        }
        catch (exception: SQLiteConstraintException) {
            updateHourlyWeather(dataWeatherData)
        }
    }

    @Query("SELECT * FROM dayweather WHERE cityId = :city AND date = :date")
    suspend fun getDayWeatherByCityAndDate(city: Int, date: String): DayWeather

    @Query("SELECT * FROM dayweather JOIN location ON location.id = dayweather.cityId WHERE dayweather.cityId = :cityId LIMIT :daysAmount")
    suspend fun getWeatherForecast(cityId: Int, daysAmount: Int): List<DayWeather>

    @Query("SELECT * FROM hourweather WHERE hourweather.dayWeatherId = :dayWeatherId")
    suspend fun getHourlyWeather(dayWeatherId: Int): List<HourWeather>
}