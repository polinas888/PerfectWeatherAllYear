package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.Location
import io.reactivex.Observable
import javax.inject.Inject

class LocalLocationDataSource @Inject constructor(locationDao: LocationDao) : LocationDao {

    private val localService: LocationDao by lazy {
        locationDao
    }

    override fun insertLocations(locations: List<Location>) {
        localService.insertLocations(locations)
    }

    override fun getLocationIdByCityName(city: String): Observable<Int> {
        return localService.getLocationIdByCityName(city)
    }

    override fun getCityNameByCityId(cityId: Int): Observable<String> {
        return localService.getCityNameByCityId(cityId)
    }

    override fun getLocations(): Observable<List<Location>?> {
        return localService.getLocations()
    }
}