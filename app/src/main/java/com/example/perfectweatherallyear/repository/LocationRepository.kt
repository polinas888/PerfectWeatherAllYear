package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location

interface LocationRepository {
    suspend fun insertLocations(locations: List<Location>)
    suspend fun getLocationIdByCityName(city: String): Int
    suspend fun getCityNameByCityId(cityId: Int): String
}