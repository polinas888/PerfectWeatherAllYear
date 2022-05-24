package com.example.perfectweatherallyear.model

import androidx.room.*

@Entity(indices = [Index(value = ["date", "cityId"], unique = true)],
    foreignKeys = [ForeignKey(entity = Location::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("cityId"),
    onDelete = ForeignKey.CASCADE)]
)
data class DayWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val cityId: Int,
    @Embedded
    val generalWeather: GeneralWeather
)