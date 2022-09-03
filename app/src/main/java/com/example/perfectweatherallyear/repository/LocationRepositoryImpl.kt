package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocationDataSource
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDataSource: LocationDataSource
) : LocationRepository {
    private val mockLocations: List<Location> = listOf(Location(name = "Moscow"), Location(name = "London"),
        Location(name = "New-York"))

    override suspend fun insertLocations(locations: List<Location>) {
        localLocationDataSource.insertLocations(locations)
    }

    override suspend fun getLocationIdByCityName(city: String): Int {
        return localLocationDataSource.getLocationIdByCityName(city)
    }

    override suspend fun getCityNameByCityId(cityId: Int): String {
        return localLocationDataSource.getCityNameByLocationId(cityId)
    }

    override suspend fun loadUserLocations(): List<Location> {
        localLocationDataSource.insertLocations(mockLocations)
        return localLocationDataSource.getLocations()
    }
}