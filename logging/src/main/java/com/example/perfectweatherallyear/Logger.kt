package com.example.perfectweatherallyear

interface Logger {
    fun createStackElementTag(element: StackTraceElement): String
    fun d(message: String, t: Throwable? = null)
    fun i(message: String, t: Throwable? = null)
}

