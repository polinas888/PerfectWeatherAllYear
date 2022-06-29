package com.example.perfectweatherallyear.utils

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import kotlinx.coroutines.*
import java.lang.Runnable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastService : Service() {
    @Inject
    lateinit var repository: WeatherRepository
    lateinit var dayWeatherForNotification: DayWeather
    lateinit var hourWeatherForNotification: HourWeather
    private lateinit var jobNotification: Job
    private lateinit var mRunnable: Runnable

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        this.appComponent.inject(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var notification = Notification()
        mRunnable = Runnable{
            jobNotification = CoroutineScope(Dispatchers.Default).launch {
                notification = NotificationHandler.createNotification(
                    application,
                    applicationContext,
                    loadHourWeatherTempForNotification()
                )
                startForeground(NOTIFICATION_ID, notification)
                delay(5000)
            }
        }
        mRunnable.run()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        super.onDestroy()
        jobNotification.cancel()
    }

    private suspend fun loadDayWeatherForNotification(): DayWeather {
        val location = Location(1, "Moscow")
        when (val weatherForecast = repository.getWeatherForecast(location, DAYS_NUMBER)) {
            is DataResult.Ok -> {
                dayWeatherForNotification =
                    weatherForecast.response[weatherForecast.response.size - 1]
            }
            is DataResult.Error ->
                weatherForecast.error
        }
        return dayWeatherForNotification
    }

    private suspend fun loadHourWeatherTempForNotification(): String {
        var hourWeatherTempForNotification = ""
        when (val hourlyWeatherForecast =
            repository.getHourlyWeather(DAYS_NUMBER, loadDayWeatherForNotification())) {
            is DataResult.Ok -> {
                val sdf = SimpleDateFormat("HH:mm:ss")
                val currentHour: String = sdf.format(Date()).take(2)
                for (hourWeather in hourlyWeatherForecast.response) {
                    val hour = hourWeather.time.substringAfterLast(" ").take(2)
                    if (hour == currentHour) {
                        hourWeatherForNotification = hourWeather
                    }
                }
                hourWeatherTempForNotification =
                    "temperature ${hourWeatherForNotification.temperature}"
            }
            is DataResult.Error -> hourlyWeatherForecast.error
        }
        return hourWeatherTempForNotification
    }
}
