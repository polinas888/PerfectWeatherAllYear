package com.example.perfectweatherallyear.repository

import com.example.perfectweatherallyear.fakes.FakeConnectionDetector
import com.example.perfectweatherallyear.fakes.FakeLocationDataSource
import com.example.perfectweatherallyear.fakes.FakeRemoteWeatherDataSource
import com.example.perfectweatherallyear.fakes.FakeWeatherDataSource
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.example.perfectweatherallyear.model.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class WeatherRepositoryImpTest {
    private val localForecastWeather = mutableListOf(
        DayWeather(
            1, "10-01-2000", 1,
            GeneralWeather("+7", "+15", "3", "11")
        ),
        DayWeather(
            2, "11-01-2000", 2,
            GeneralWeather("+8", "+16", "4", "12")
        )
    )

    private val remoteForecastWeather = mutableListOf(
        DayWeather(
            3, "13-01-2000", 1,
            GeneralWeather("+15", "+20", "4", "10")
        ),
        DayWeather(
            4, "14-01-2000", 2,
            GeneralWeather("+18", "+26", "5", "12")
        )
    )

    val locations = mutableListOf(Location(1, "Moscow"), Location(2, "London"))

    private lateinit var remoteWeatherDataSource: FakeRemoteWeatherDataSource
    private lateinit var weatherDataSource: FakeWeatherDataSource
    private lateinit var locationDataSource: FakeLocationDataSource
    private lateinit var repository: WeatherRepositoryImp
    private lateinit var connectionDetector: FakeConnectionDetector

    @Before
    fun createRepository() {
        remoteWeatherDataSource = FakeRemoteWeatherDataSource(remoteForecastWeather)
        weatherDataSource = FakeWeatherDataSource(localForecastWeather)
        locationDataSource = FakeLocationDataSource(locations)
        connectionDetector = FakeConnectionDetector()

        repository = WeatherRepositoryImp(
            remoteWeatherDataSource,
            weatherDataSource,
            locationDataSource,
            connectionDetector
        )
    }

    @Test
    fun getWeatherForecastWithConnection_returnUpdatedWeatherList() = runTest {
        val listWeather = repository.getWeatherForecast(Location(1, "Moscow"), 2) as DataResult.Ok
        assertThat(
            listWeather.response, IsEqual(
                listOf(
                    DayWeather(
                        1, "10-01-2000", 1,
                        GeneralWeather("+7", "+15", "3", "11")
                    ), DayWeather(
                        3, "13-01-2000", 1,
                        GeneralWeather("+15", "+20", "4", "10")
                    )
                )
            )
        )
    }

    @Test
    fun getWeatherForecastNoConnection_returnUpdatedWeatherList() = runTest {
        connectionDetector.setOrTurnOffConnection(false)
        val result = repository.getWeatherForecast(Location(1, "Moscow"), 2) as DataResult.Ok
        assertThat(
            result.response, IsEqual(
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