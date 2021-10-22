package com.example.perfectweatherallyear.repository.CityData

import com.example.perfectweatherallyear.model.City

class CityRepositoryImp: CityRepository {

    override suspend fun getCity(id: String): List<City> {
        return listOf(City(1, "Moscow"))
    }

    override suspend fun getAllCities(): List<City> {
        return listOf(
            City(1, "Moscow"),
            City(2, "New-York"),
            City(3, "Nizchiy-Novgorod"))
    }
}