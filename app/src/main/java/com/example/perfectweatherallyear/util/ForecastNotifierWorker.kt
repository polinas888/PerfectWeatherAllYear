package com.example.perfectweatherallyear.util

import android.content.Context
import android.util.Log
import androidx.work.*
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
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
    var isReady: DataResult<Any>? = null

    init {
        appContext.appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        loadHourWeather()
        while (isReady == null) {
            Thread.sleep(1000)
        }
        return if (isReady == DataResult.Ok(Any()))
            success()
        else failure()
    }

    private fun loadHourWeather() {
        val location = Location(1, "Moscow")
        compositeDisposable.add(
            repository.getRemoteWeatherForecast(location.name, DAYS_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<DayWeather>>() {
                    override fun onNext(_listDayWeather: List<DayWeather>) {
                        val dayWeatherForNotification = _listDayWeather[_listDayWeather.size - 1]
                        getHourlyWeatherTemp(DAYS_NUMBER, dayWeatherForNotification, location.name)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("UpdateWeatherLog", "Couldn't update weather")
                    }

                    override fun onComplete() {
                        Log.i("UpdateWeatherLog", "Got weather")
                    }
                })
        )
    }

    fun getHourlyWeatherTemp(numDay: Int, dayWeather: DayWeather, cityName: String) {
        compositeDisposable.add(
            repository.getRemoteHourlyWeather(numDay, dayWeather, cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<HourWeather>>() {
                    override fun onNext(_listHourWeather: List<HourWeather>) {
                        val temperatureForNotification =
                            getTemperatureForNotification(_listHourWeather)
                        NotificationHandler.postNotification(applicationContext, temperatureForNotification)
                        isReady = DataResult.Ok(Any())
                    }

                    override fun onError(e: Throwable) {
                        Log.i("UpdateWeatherLog", "Couldn't update weather")
                        isReady = DataResult.Error("error")
                    }

                    override fun onComplete() {
                        Log.i("UpdateWeatherLog", "Got weather")
                    }
                })
        )
    }

    private fun getTemperatureForNotification(hourlyWeatherForecast: List<HourWeather>):String {
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