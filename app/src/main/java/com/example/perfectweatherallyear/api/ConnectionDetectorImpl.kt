package com.example.perfectweatherallyear.api

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class ConnectionDetectorImpl @Inject constructor(private val context: Context) :
    ConnectionDetector {

    override fun isConnectingToInternet(): Boolean {
        val cm: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo?.isConnected == true) {
            return true
        }
        return false
    }
}