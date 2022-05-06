package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSource
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDataSource: LocalLocationDataSource
) : LocationRepository {
    private val mockLocations: List<Location> = listOf(Location(name = "Moscow"), Location(name = "London"),
        Location(name = "New-York"))

    override suspend fun insertLocations(locations: List<Location>) {
        localLocationDataSource.insertLocation(locations)
    }

    override suspend fun getLocationIdByCityName(city: String): Int {
        return localLocationDataSource.getLocationIdByCityName(city)
    }

    override suspend fun getCityNameByCityId(cityId: Int): String {
        return localLocationDataSource.getCityNameByCityId(cityId)
    }

    override suspend fun loadUserLocations(): List<Location> {
        localLocationDataSource.insertLocation(mockLocations)
        return localLocationDataSource.getLocations()
    }
}