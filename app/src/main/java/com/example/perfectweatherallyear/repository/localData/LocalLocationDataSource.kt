package com.example.perfectweatherallyear.repository.localData

import com.example.perfectweatherallyear.model.Location
import javax.inject.Inject

class LocalLocationDataSource @Inject constructor(locationDao: LocationDao) : LocationDao {
    private val localService: LocationDao by lazy {
        locationDao
    }

    override suspend fun insertLocation(locations: List<Location>) {
        localService.insertLocation(locations)
    }

    override fun getLocationIdByCityName(city: String): Int {
        return localService.getLocationIdByCityName(city)
    }

    override suspend fun getCityNameByCityId(cityId: Int): String {
        return localService.getCityNameByCityId(cityId)
    }

    override suspend fun getLocations(): List<Location> {
        return localService.getLocations()
    }
}