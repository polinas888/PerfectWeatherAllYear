package com.example.perfectweatherallyear.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.perfectweatherallyear.appComponent
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.Location
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: WeatherRepository

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.applicationContext?.appComponent?.inject(this)
        val pendingResult: PendingResult = goAsync()

        GlobalScope.launch {
            try {
                getForecast(Location(1, "Moscow"))
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "We got forecast", Toast.LENGTH_LONG).show()
                }
            } catch (exception: Exception) {
                Log.e("onReceive", exception.message.toString())
            } finally {
                pendingResult.finish()
            }
        }
    }

    private suspend fun getForecast(location: Location): List<DayWeather> {
        var weatherForecast: List<DayWeather> = mutableListOf()
        try {
            when ( val weatherForecast1 = repository.getWeatherForecast(location, DAYS_NUMBER)) {
                is DataResult.Ok -> {
                    weatherForecast = weatherForecast1.response!!
                }
                is DataResult.Error ->
                    Log.e("onReceiver", "no data")
            }
        } catch (exception: Exception) {
            Log.e("onReceiver", "Failed")
        }
        return weatherForecast
    }
}