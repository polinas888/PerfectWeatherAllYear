package com.example.perfectweatherallyear.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = ["id"], unique = true)])
data class Location(
    val id: String = UUID.randomUUID().toString(),
    @PrimaryKey
    var name: String
)