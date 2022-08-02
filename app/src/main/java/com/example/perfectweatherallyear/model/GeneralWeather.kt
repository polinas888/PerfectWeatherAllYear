package com.example.perfectweatherallyear.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralWeather(
    val temperatureMin: String,
    val temperatureMax: String,
    val precipitation: String,
    val wind: String
): Parcelable