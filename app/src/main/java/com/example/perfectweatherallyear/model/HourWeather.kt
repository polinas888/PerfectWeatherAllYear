package com.example.perfectweatherallyear.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (foreignKeys = [ForeignKey(entity = DayWeather::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("dayWeatherId"))],
    indices = [Index(value = ["time", "dayWeatherId"], unique = true)]
    )
data class HourWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time: String,
    var temperature: String,
    var precipitation: String,
    var wind: String,
    val dayWeatherId: Int
)