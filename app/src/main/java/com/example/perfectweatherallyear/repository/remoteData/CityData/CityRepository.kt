package com.example.perfectweatherallyear.repository.remoteData.CityData

import com.example.perfectweatherallyear.model.City

interface CityRepository {
    suspend fun getCity(id: String): List<City>

    suspend fun getAllCities(): List<City>
}