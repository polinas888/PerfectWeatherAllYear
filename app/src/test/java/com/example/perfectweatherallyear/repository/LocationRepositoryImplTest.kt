package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.FakeLocationDataSource
import com.example.perfectweatherallyear.model.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationRepositoryImplTest {
    private val locations: MutableList<Location> = mutableListOf(Location(id = 1, name = "Moscow"), Location(id = 2, name = "London"))

    private lateinit var locationDataSource: FakeLocationDataSource
    private lateinit var locationRepository: LocationRepositoryImpl

    @Before
    fun createRepository() {
        locationDataSource = FakeLocationDataSource(locations)
        locationRepository = LocationRepositoryImpl(locationDataSource)
    }

    @Test
    fun insertLocations_addLocation_listLocationsContainsPreviousAndAddedLocation() {
        runTest {
            locationRepository.insertLocations(mutableListOf(Location(id = 3, "Tokio")))
            assertThat(locations, IsEqual(mutableListOf(
                Location(id = 1, name = "Moscow"),
                Location(id = 2, name = "London"),
                Location(id = 3, "Tokio")
            )))
        }
    }

    @Test
    fun getLocationIdByCityName_passCityName_returnLocationIdForThisCity() = runTest {
        val locationId = locationRepository.getLocationIdByCityName(locations[0].name)
        assertThat(locationId, IsEqual(locations[0].id))
    }

    @Test
    fun getCityNameByCityId_passCityId_returnCityNameForThisCityId() = runTest {
        val cityName = locationRepository.getCityNameByCityId(locations[0].id)
        assertThat(cityName, IsEqual(locations[0].name))
    }

    @Test
    fun loadUserLocations_returnUpdatedListLocations() = runTest {
        val loadUserLocations = locationRepository.loadUserLocations()
        assertThat(loadUserLocations, IsEqual(mutableListOf(
            Location(id = 1, name = "Moscow"),
            Location(id = 2, name = "London"),
            Location(name = "Moscow"),
            Location(name = "London"),
            Location(name = "New-York")
        )))
    }
}