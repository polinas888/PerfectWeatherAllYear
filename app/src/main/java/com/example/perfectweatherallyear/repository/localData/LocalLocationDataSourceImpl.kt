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

    override suspend fun getCityNameByCityId(cityId: Int): String {
        return localService.getCityNameByCityId(cityId)
    }

    override suspend fun getLocations(): List<Location> {
        return localService.getLocations()
    }
}