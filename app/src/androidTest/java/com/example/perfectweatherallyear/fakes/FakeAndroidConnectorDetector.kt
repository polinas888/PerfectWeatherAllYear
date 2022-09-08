package com.example.perfectweatherallyear.fakes

import com.example.perfectweatherallyear.api.ConnectionDetector

class FakeAndroidConnectorDetector : ConnectionDetector {
    var isConnected: Boolean = true

    fun setOrTurnOffConnection(isConnected: Boolean) {
        this.isConnected = isConnected
    }

    override fun isConnectingToInternet(): Boolean {
        return isConnected
    }
}