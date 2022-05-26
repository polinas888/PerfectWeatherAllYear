package com.example.perfectweatherallyear.util

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.success
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerHandler(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    @Inject
    lateinit var repository: WeatherRepository

    init {
        appContext.appComponent.inject(this)
    }

    override fun doWork(): Result {
        GlobalScope.launch {
            NotificationHandler.createNotification(applicationContext, loadForecastForNotification())
        }

        return success()
    }

    companion object {
        private val WEATHER_TAG = "PeriodicWorkTag"

        fun createWorker(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false)
                .build()

            val work = PeriodicWorkRequestBuilder<WorkerHandler>(4, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()
            val workManager = WorkManager.getInstance(context)
                workManager.enqueueUniquePeriodicWork(WEATHER_TAG, ExistingPeriodicWorkPolicy.REPLACE, work)
        }
    }

    private suspend fun loadForecastForNotification(): String {
        var lastWeather = ""
            val location = Location(1, "Moscow")
            when (val weatherForecast = repository.getWeatherForecast(location, DAYS_NUMBER)) {
                is DataResult.Ok -> {
                    lastWeather = "max temp ${weatherForecast.response[weatherForecast.response.size - 1]
                        .generalWeather.temperatureMax} min temp ${weatherForecast.response[weatherForecast.
                    response.size - 1].generalWeather.temperatureMin}"
                }
                is DataResult.Error ->
                    weatherForecast.error
            }
        return lastWeather
    }
}