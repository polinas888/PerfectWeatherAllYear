package com.example.perfectweatherallyear.api

import android.content.Context
import android.net.ConnectivityManager

class ConnectionDetector(private val context: Context) {

    fun isConnectingToInternet(): Boolean {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo?.isConnected == true) {
            return true
        }
        return false
    }
}