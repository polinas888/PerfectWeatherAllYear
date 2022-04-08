package com.example.perfectweatherallyear.repository

sealed class DataResult<out R>
data class SuccessResult<out T>(val data: T) : DataResult<T>()
data class FailureResult(val exception: String) : DataResult<Nothing>()