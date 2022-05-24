package com.example.perfectweatherallyear.repository.localData

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "weather-database"
class DatabaseFactory private constructor(context: Context){

    val database : WeatherDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    companion object {
        private var INSTANCE: DatabaseFactory? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = DatabaseFactory(context)
            }
        }

        fun get() : DatabaseFactory {
            return INSTANCE ?:
            throw IllegalStateException("WeatherLocalRepository must be initialized")
        }
    }
}