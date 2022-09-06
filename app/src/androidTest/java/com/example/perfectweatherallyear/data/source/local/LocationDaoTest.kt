package com.example.perfectweatherallyear.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.WeatherDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocationDaoTest {
    private lateinit var database: WeatherDatabase

        @get:Rule
        var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() = runTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).build()

        val locations = listOf(Location(1, "London"), Location(2, "Moscow"))
        database.locationDao().insertLocations(locations)
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertLocationAndGetLocations() = runTest {
        database.locationDao().insertLocations(listOf(Location(3, "New-York")))
        val locationsLoaded = database.locationDao().getLocations()

        assertThat(locationsLoaded, CoreMatchers.notNullValue())
        assertThat(locationsLoaded, IsEqual(listOf(Location(1, "London"),
            Location(2, "Moscow"), Location(3, "New-York"))))
    }

    @Test
    fun getLocationIdByCityName() = runTest {
        val locationId = database.locationDao().getLocationIdByCityName("London")
        assertThat(locationId, `is`(1))
    }

    @Test
    fun getCityNameByLocationId() = runTest {
        val cityName = database.locationDao().getCityNameByLocationId(1)
        assertThat(cityName, `is`("London"))
    }
}
