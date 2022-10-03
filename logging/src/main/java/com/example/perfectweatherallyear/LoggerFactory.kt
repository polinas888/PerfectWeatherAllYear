package com.example.perfectweatherallyear

interface LoggerFactory {
    fun createLogger(loggerType: LoggerType): Logger
}

class LoggerFactoryImpl : LoggerFactory {
    override fun createLogger(loggerType: LoggerType): Logger =
        when (loggerType) {
            LoggerType.TimberLogger -> TimberLogging()
        }
}

enum class LoggerType {
    TimberLogger
}