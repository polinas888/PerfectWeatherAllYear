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

    override suspend fun getLocationIdByCityName(city: String): String {
        return localLocationDataSource.getLocationIdByCityName(city)
    }

    override suspend fun getCityNameByCityId(cityId: String): String {
        return localLocationDataSource.getCityNameByCityId(cityId)
    }
}