package com.example.perfectweatherallyear

import com.example.perfectweatherallyear.api.ConnectionDetector

class FakeConnectionDetector: ConnectionDetector {
    var isConnected: Boolean = true

    fun setOrTurnOffConnection(isConnected: Boolean) {
        this.isConnected = isConnected
    }

    override fun isConnectingToInternet(): Boolean {
       return isConnected
    }
}