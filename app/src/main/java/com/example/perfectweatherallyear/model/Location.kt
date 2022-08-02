package com.example.perfectweatherallyear.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["name"], unique = true)])
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String
): Parcelable