package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import kotlinx.coroutines.launch

class DetailWeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    val detailWeatherLiveData = MutableLiveData<List<HourWeather>>()

    fun loadData(dayWeather: DayWeather) {
        viewModelScope.launch {
            when (val hourlyWeather = getHourlyWeather(DAYS_NUMBER, dayWeather.cityId, dayWeather.date, dayWeather.id)) {
                is DataResult.Ok -> {
                    detailWeatherLiveData.value = hourlyWeather.response!!
                }
                is DataResult.Error -> hourlyWeather.error
            }
        }
    }

    private suspend fun getHourlyWeather(daysAmount: Int, cityId: Int, date: String, dayWeatherId: Int)
            : DataResult<List<HourWeather>> {
        return repository.getHourlyWeather(daysAmount, cityId, date, dayWeatherId)
    }
}