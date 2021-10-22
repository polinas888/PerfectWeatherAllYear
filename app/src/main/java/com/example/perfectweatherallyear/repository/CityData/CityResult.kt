package com.example.perfectweatherallyear.repository.CityData

sealed class CityResult {
    data class Ok(val response: List<CityResult>) : CityResult()
    data class Error(val error: String?) : CityResult()
}
