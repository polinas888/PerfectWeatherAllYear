package com.example.perfectweatherallyear.util

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.success
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerHandler(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    @Inject
    lateinit var repository: WeatherRepository
    lateinit var dayWeatherForNotification: DayWeather
    lateinit var hourWeatherForNotification:  HourWeather

    init {
        appContext.appComponent.inject(this)
    }

    override fun doWork(): Result {
        GlobalScope.launch {
            NotificationHandler.createNotification(applicationContext, loadHourWeatherTempForNotification())
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

    private suspend fun loadDayWeatherForNotification(): DayWeather {
            val location = Location(1, "Moscow")
          when (val weatherForecast = repository.getWeatherForecast(location, DAYS_NUMBER)) {
                is DataResult.Ok -> {
                    dayWeatherForNotification = weatherForecast.response[0]
                }
                is DataResult.Error ->
                    weatherForecast.error
            }
        return dayWeatherForNotification
    }

    private suspend fun loadHourWeatherTempForNotification(): String {
        var hourWeatherTempForNotification = ""
            when (val hourlyWeatherForecast = repository.getHourlyWeather(DAYS_NUMBER, loadDayWeatherForNotification())) {
                is DataResult.Ok -> {
                    val sdf = SimpleDateFormat("HH:mm:ss")
                    val currentHour: String = sdf.format(Date()).take(2)
                    for (hourWeather in hourlyWeatherForecast.response) {
                        val hour = hourWeather.time.substringAfterLast(" ").take(2)
                        if(hour == currentHour) {
                            hourWeatherForNotification = hourWeather
                        }
                    }
                    hourWeatherTempForNotification = "temperature ${hourWeatherForNotification.temperature}"
                }
                is DataResult.Error -> hourlyWeatherForecast.error
            }
        return hourWeatherTempForNotification
    }
}