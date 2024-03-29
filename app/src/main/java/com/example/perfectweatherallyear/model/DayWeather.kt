package com.example.perfectweatherallyear.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(indices = [Index(value = ["date", "cityId"], unique = true)],
    foreignKeys = [ForeignKey(entity = Location::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("cityId"),
    onDelete = ForeignKey.CASCADE)]
)

@Parcelize
data class DayWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val cityId: Int,
    @Embedded
    val generalWeather: GeneralWeather
): Parcelable