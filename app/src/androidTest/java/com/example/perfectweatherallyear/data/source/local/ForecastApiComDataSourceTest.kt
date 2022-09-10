package com.example.perfectweatherallyear.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.GeneralWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.remoteData.weatherData.ForecastApiComDataSource
import com.example.perfectweatherallyear.repository.remoteData.weatherData.WeatherApiCom
import com.example.perfectweatherallyear.utils.MainAndroidCoroutineRule
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class ForecastApiComDataSourceTest {
    private val mockWebServer = MockWebServer()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainAndroidCoroutineRule = MainAndroidCoroutineRule()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiCom::class.java)

    private val forecastApiDataSource = ForecastApiComDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldFetchWeatherCorrectlyGiven200Response() {

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(
                Gson().toJson(
                    DayWeather(
                        1, "10-01-2000", 1,
                        GeneralWeather("+7", "+15", "3", "11")
                    )
                )
            )
        )

        runBlocking {
            val actual = forecastApiDataSource.getWeatherForecast(Location(1, "Moscow"), 3)

            assertEquals(
                listOf(
                    DayWeather(
                        1, "10-01-2000", 1,
                        GeneralWeather("+7", "+15", "3", "11")
                    )
                ), actual
            )
        }
    }
}