package com.example.perfectweatherallyear.util

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastNotifierWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    @Inject
    lateinit var repository: WeatherRepository

    init {
        appContext.appComponent.inject(this)
    }

    override suspend fun doWork(): Result {

        return when (val hourlyWeatherForNotificationResult = loadHourWeatherTempForNotification()) {
            is DataResult.Ok -> {
                NotificationHandler.postNotification(applicationContext, hourlyWeatherForNotificationResult.response)
                success()
            }
            is DataResult.Error -> {
                hourlyWeatherForNotificationResult.error
                failure()
            }
        }
    }

    private suspend fun loadDayWeatherForNotification(): DayWeather? {
        val location = Location(1, "Moscow")
        return when (val weatherForecast = repository.getWeatherForecast(location, DAYS_NUMBER)) {
            is DataResult.Ok -> {
                weatherForecast.response[weatherForecast.response.size - 1]
            }
            is DataResult.Error ->
                null
        }
    }

    private suspend fun loadHourWeatherTempForNotification(): DataResult<String> {
        var hourWeatherTempForNotification = ""
        return when (val hourlyWeatherForecast =
            loadDayWeatherForNotification()?.let { repository.getHourlyWeather(DAYS_NUMBER, it) }) {
            is DataResult.Ok -> {
                val sdf = SimpleDateFormat("HH:mm:ss")
                val currentHour: String = sdf.format(Date()).take(2)
                for (hourWeather in hourlyWeatherForecast.response) {
                    val hour = hourWeather.time.substringAfterLast(" ").take(2)
                    if (hour == currentHour) {
                        hourWeatherTempForNotification = "temperature ${hourWeather.temperature}"
                    }
                }
                DataResult.Ok(hourWeatherTempForNotification)
            }
            is DataResult.Error -> DataResult.Error(hourlyWeatherForecast.error)
            else -> {
                DataResult.Error("No hourly weather")
            }
        }
    }

    companion object {
        private val WEATHER_TAG = "PeriodicWorkTag"

        fun createWorker(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false)
                .build()

            val work = PeriodicWorkRequestBuilder<ForecastNotifierWorker>(4, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniquePeriodicWork(
                WEATHER_TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                work
            )
        }
    }
}