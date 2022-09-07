package com.example.perfectweatherallyear.api

interface ConnectionDetector {
    fun isConnectingToInternet(): Boolean
}