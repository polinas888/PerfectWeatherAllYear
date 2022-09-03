package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.Location
import javax.inject.Inject

class LocalLocationDataSourceImpl @Inject constructor(locationDao: LocationDao) : LocationDataSource {

    private val localService: LocationDao by lazy {
        locationDao
    }

    override suspend fun insertLocations(locations: List<Location>) {
        localService.insertLocations(locations)
    }

    override suspend fun getLocationIdByCityName(city: String): Int {
        return localService.getLocationIdByCityName(city)
    }

    override suspend fun getCityNameByLocationId(locationId: Int): String {
        return localService.getCityNameByLocationId(locationId)
    }

    override suspend fun getLocations(): List<Location> {
        return localService.getLocations()
    }
}