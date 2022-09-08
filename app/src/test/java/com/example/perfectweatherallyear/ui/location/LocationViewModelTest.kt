package com.example.perfectweatherallyear.ui.location

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.perfectweatherallyear.utils.MainCoroutineRule
import com.example.perfectweatherallyear.getOrAwaitValue
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.fakes.FakeLocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationViewModelTest {
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationRepository: FakeLocationRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() = runTest {
        locationRepository = FakeLocationRepository()
        val locations = listOf(Location(1,"London"), Location(2, "New-York"))
        locationRepository.insertLocations(locations)
        locationViewModel = LocationViewModel(locationRepository)
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadLocations_listLocationsLiveDataValueEqualsListInRepository() = runTest {
        locationViewModel.loadLocations()
        val locations = locationViewModel.listLocationsLiveData.getOrAwaitValue()
        MatcherAssert.assertThat(
            locations, IsEqual(
                mutableListOf(
                    Location(1, "London"),
                    Location(2, "New-York"),
                    Location(3, "Moscow")
                )
            )
        )
    }
}