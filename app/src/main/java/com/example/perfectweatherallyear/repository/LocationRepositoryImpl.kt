package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSource
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDataSource: LocalLocationDataSource
) : LocationRepository {

    override suspend fun insertLocations(locations: List<Location>) {
        localLocationDataSource.insertLocation(locations)
    }

    override suspend fun getLocationIdByCityName(city: String): Int {
        return localLocationDataSource.getLocationIdByCityName(city)
    }

    override suspend fun getCityNameByCityId(cityId: Int): String {
        return localLocationDataSource.getCityNameByCityId(cityId)
    }
}