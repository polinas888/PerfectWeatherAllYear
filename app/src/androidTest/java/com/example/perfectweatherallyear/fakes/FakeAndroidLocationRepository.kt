package com.example.perfectweatherallyear.fakes

import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.LocationRepository
import com.example.perfectweatherallyear.util.EspressoIdlingResources

class FakeAndroidLocationRepository: LocationRepository {
    var locationServiceData: MutableList<Location> = mutableListOf()

    override suspend fun insertLocations(locations: List<Location>) {
        EspressoIdlingResources.increment()
        for (location in locations) {
            locationServiceData.add(location)
        }
        EspressoIdlingResources.decrement()
    }

    override suspend fun getLocationIdByCityName(city: String): Int {
        var locationId = 0
        for(location in locationServiceData) {
            if (location.name == city) {
                locationId = location.id
            }
        }
        return locationId
    }

    override suspend fun getCityNameByCityId(cityId: Int): String {
        var cityName = ""
        for (location in locationServiceData) {
            if(location.id == cityId) {
                cityName = location.name
            }
        }
        return cityName
    }

    override suspend fun loadUserLocations(): List<Location> {
        return locationServiceData
    }
}