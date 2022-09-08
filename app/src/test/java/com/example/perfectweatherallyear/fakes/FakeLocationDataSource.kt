package com.example.perfectweatherallyear.fakes

import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocationDataSource

class FakeLocationDataSource(private val locationsList: MutableList<Location> = mutableListOf()) : LocationDataSource {

    override suspend fun insertLocations(locations: List<Location>) {
        for (location in locations) {
            locationsList.add(location)
        }
    }

    override suspend fun getLocationIdByCityName(city: String): Int {
        var locationId = 0
        for (location in locationsList) {
            if(location.name == city) {
                locationId = location.id
            }
        }
        return locationId
    }

    override suspend fun getCityNameByLocationId(cityId: Int): String {
        var cityName = ""
        for (location in locationsList) {
            if(location.id == cityId) {
                cityName = location.name
            }
        }
        return cityName
    }

    override suspend fun getLocations(): List<Location> {
        return locationsList
    }
}