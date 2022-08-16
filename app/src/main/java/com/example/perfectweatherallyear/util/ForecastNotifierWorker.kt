package com.example.perfectweatherallyear.util

import android.content.Context
import androidx.work.*
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastNotifierWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    @Inject
    lateinit var repository: WeatherRepository
    private val compositeDisposable = CompositeDisposable()
    val location = Location(1, "Moscow")

    init {
        appContext.appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        val temperatureForNotification = getTemperatureForNotification()
        return if (temperatureForNotification.isNotEmpty()){
            userNotification(applicationContext, temperatureForNotification)
            success()
        } else {
            failure()
        }
    }

    private fun userNotification(applicationContext: Context, temperatureForNotification:  String) {
        NotificationHandler.postNotification(applicationContext, temperatureForNotification)
    }

    private fun getTemperatureForNotification(): String {
        val listDayWeather = getListDayWeather()
        val listHourlyWeather = getHourlyWeather(listDayWeather, DAYS_NUMBER, location.name)
        return getTemperatureString(listHourlyWeather)
    }

    private fun getListDayWeather(): List<DayWeather> {
        compositeDisposable.add(
            return repository.getRemoteWeatherForecast(location.name, DAYS_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .blockingFirst())
    }

    private fun getHourlyWeather(listDayWeather:  List<DayWeather>, numDay: Int, cityName: String) : List<HourWeather> {
        val dayWeatherForNotification = listDayWeather[listDayWeather.size - 1]
        return repository.getRemoteHourlyWeather(numDay, dayWeatherForNotification, cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .blockingFirst()
    }

    private fun getTemperatureString(hourlyWeatherForecast: List<HourWeather>) : String {
        val sdf = SimpleDateFormat("HH:mm:ss")
        val currentHour: String = sdf.format(Date()).take(2)
        for (hourWeather in hourlyWeatherForecast) {
            val hour = hourWeather.time.substringAfterLast(" ").take(2)
             if (hour == currentHour) {
                 return "temperature ${hourWeather.temperature}"
            }
        }
        return ""
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