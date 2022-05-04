package com.example.perfectweatherallyear.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(indices = [Index(value = ["id"], unique = true)],
    foreignKeys = [ForeignKey(entity = Location::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("cityId"),
    onDelete = ForeignKey.CASCADE)], primaryKeys = ["date", "cityId"]
)
data class DayWeather(
    val id: String = UUID.randomUUID().toString(),
    val date: String,
    val cityId: String = UUID.randomUUID().toString(),
    @Embedded
    val generalWeather: GeneralWeather
)