package com.example.perfectweatherallyear.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class WeatherBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            ForecastNotifierWorker.createWorker(context)
        }
    }
}