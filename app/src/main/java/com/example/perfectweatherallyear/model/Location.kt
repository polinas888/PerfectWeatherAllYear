package com.example.perfectweatherallyear.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String
)