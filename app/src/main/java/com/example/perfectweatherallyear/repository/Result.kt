package com.example.perfectweatherallyear.repository

sealed class Result<out R> {
    data class Ok<out T>(val response: T) : Result<T>()
    data class Error(val error: String?) : Result<Nothing>()
}