package com.example.perfectweatherallyear.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity (foreignKeys = [ForeignKey(entity = DayWeather::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("dayWeatherId"),
    onDelete = ForeignKey.CASCADE)],
    primaryKeys = [ "time", "dayWeatherId" ])
data class HourWeather (
    var time: String,
    var temperature: String,
    var precipitation: String,
    var wind: String,
    val dayWeatherId: String
)