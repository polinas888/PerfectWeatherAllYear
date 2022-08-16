package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location
import io.reactivex.Observable

interface LocationRepository {
    fun insertLocations(locations: List<Location>)
    fun getLocationIdByCityName(city: String): Int
    fun getCityNameByCityId(cityId: Int): Observable<String>
    fun loadUserLocations(): Observable<List<Location>?>
}