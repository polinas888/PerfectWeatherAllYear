package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.Location

interface LocationDataSource {

    suspend fun insertLocations(locations: List<Location>)

    suspend fun getLocationIdByCityName(city: String): Int

    suspend fun getCityNameByCityId(cityId: Int): String

    suspend fun getLocations(): List<Location>
}