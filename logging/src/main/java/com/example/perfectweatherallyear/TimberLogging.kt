package com.example.perfectweatherallyear

import timber.log.Timber

class TimberLogging: Timber.DebugTree(), Logger {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber}) on ${element.methodName}"
    }

    override fun d(message: String, t: Throwable?) = this.d(t, message)

    override fun i(message: String, t: Throwable?) = this.i(t, message)

}