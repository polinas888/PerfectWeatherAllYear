package com.example.perfectweatherallyear.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.localData.LocalWeatherDataSourceImpl
import com.example.perfectweatherallyear.repository.localData.WeatherDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalWeatherDataSourceImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSourceImpl: LocalWeatherDataSourceImpl
    private lateinit var database: WeatherDatabase

    @Before
    fun setup() = runTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        localDataSourceImpl = LocalWeatherDataSourceImpl(database.weatherDao())

        val locations = listOf(Location(1, "Moscow"), Location(2, "London"))
        database.locationDao().insertLocations(locations)
        val forecastWeather = listOf(
            DayWeather(
                1, "10-01-2000", 1,
                GeneralWeather("+7", "+15", "3", "11")
            ),
            DayWeather(
                2, "11-01-2000", 2,
                GeneralWeather("+8", "+16", "4", "12")
            )
        )
        database.weatherDao().insertDayWeather(forecastWeather)
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun insertDayWeather_andGetDayWeather() = runTest {
        localDataSourceImpl.insertDayWeather(
            listOf(
                DayWeather(
                    3, "12-01-2000", 1,
                    GeneralWeather("+9", "+17", "5", "13")
                )
            )
        )
        val dayWeatherList = localDataSourceImpl.getDayWeather()
        MatcherAssert.assertThat(dayWeatherList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            dayWeatherList, IsEqual(
                listOf(
                    DayWeather(
                        1, "10-01-2000", 1,
                        GeneralWeather("+7", "+15", "3", "11")
                    ), DayWeather(
                        2, "11-01-2000", 2,
                        GeneralWeather("+8", "+16", "4", "12")
                    ),
                    DayWeather(
                        3, "12-01-2000", 1,
                        GeneralWeather("+9", "+17", "5", "13")
                    )
                )
            )
        )
    }

    @Test
    fun getDayWeatherByCityAndDate() = runTest {
        val dayWeather = localDataSourceImpl.getDayWeatherByCityAndDate(1, "10-01-2000")
        MatcherAssert.assertThat(
            dayWeather, IsEqual(
                DayWeather(
                    1, "10-01-2000", 1,
                    GeneralWeather("+7", "+15", "3", "11")
                )
            )
        )
    }

    @Test
    fun getDayWeatherForSelectedCityForPeriod() = runTest {
        val dayWeatherList = localDataSourceImpl.getWeatherForecast(1, 1)
        MatcherAssert.assertThat(
            dayWeatherList, IsEqual(
                listOf(
                    DayWeather(
                        1, "10-01-2000", 1,
                        GeneralWeather("+7", "+15", "3", "11")
                    )
                )
            )
        )
    }
}