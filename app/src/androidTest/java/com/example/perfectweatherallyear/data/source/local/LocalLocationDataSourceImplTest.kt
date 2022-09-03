package com.example.perfectweatherallyear.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalLocationDataSourceImpl
import com.example.perfectweatherallyear.repository.localData.WeatherDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalLocationDataSourceImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: LocalLocationDataSourceImpl
    private lateinit var database: WeatherDatabase

    @Before
    fun setup() = runTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        localDataSource =
            LocalLocationDataSourceImpl(database.locationDao())

        val locations = listOf(Location(1, "London"), Location(2, "Moscow"))
        database.locationDao().insertLocations(locations)
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun insertLocations_getLocations() = runTest {
        val result = localDataSource.getLocations()

        assertThat(result, CoreMatchers.notNullValue())
        assertThat(result, IsEqual(listOf(Location(1, "London"),
            Location(2, "Moscow")))
        )
    }

    @Test
    fun getLocationIdByCityName() = runTest {
        val locationId = localDataSource.getLocationIdByCityName("London")
        assertThat(locationId, CoreMatchers.`is`(1))
    }

    @Test
    fun getCityNameByLocationId() = runTest {
        val cityName = localDataSource.getCityNameByLocationId(1)
        assertThat(cityName, CoreMatchers.`is`("London"))
    }
}