package com.example.perfectweatherallyear.repository.localData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import io.reactivex.Observable

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDayWeather(dataWeatherData: List<DayWeather>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHourlyWeather(dataWeatherData: List<HourWeather>)

    @Query("SELECT * FROM dayweather WHERE cityId = :city AND date = :date")
    fun getDayWeatherByCityAndDate(city: Int, date: String): Observable<DayWeather>

    @Query("SELECT * FROM dayweather WHERE dayweather.cityId = :cityId ORDER BY ID DESC LIMIT :daysAmount")
    fun getWeatherForecast(cityId: Int, daysAmount: Int): Observable<List<DayWeather>>

    @Query("SELECT * FROM hourweather WHERE hourweather.dayWeatherId = :dayWeatherId")
    fun getHourlyWeather(dayWeatherId: Int): Observable<List<HourWeather>>
}