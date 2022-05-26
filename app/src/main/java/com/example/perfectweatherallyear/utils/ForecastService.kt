package com.example.perfectweatherallyear.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
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
import javax.inject.Inject

class ForecastService : Service() {
    @Inject
    lateinit var repository: WeatherRepository
    lateinit var dayWeatherForNotification: DayWeather
    lateinit var hourWeatherForNotification: HourWeather
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        this.appComponent.inject(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        mHandler = Handler()
        mRunnable = Runnable{
            GlobalScope.launch {
                NotificationHandler.createNotification(
                    applicationContext,
                    loadHourWeatherTempForNotification()
                )
            }
            mHandler.postDelayed(mRunnable, 10000)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
       mRunnable.run()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
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
