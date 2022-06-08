package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location
import io.reactivex.rxjava3.core.Flowable

interface LocationRepository {
    suspend fun insertLocations(locations: List<Location>)
    fun getLocationIdByCityName(city: String): Flowable<Int>
    suspend fun getCityNameByCityId(cityId: Int): String
    suspend fun loadUserLocations(): List<Location>
}