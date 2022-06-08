package com.example.perfectweatherallyear.ui.detailWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.WeatherRepository

class DetailWeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    val detailWeatherLiveData = MutableLiveData<List<HourWeather>>()

//    fun loadData(dayWeather: DayWeather) {
//        viewModelScope.launch {
//            when (val hourlyWeather = getHourlyWeather(DAYS_NUMBER, dayWeather)) {
//                is DataResult.Ok -> {
//                    detailWeatherLiveData.value = hourlyWeather.response!!
//                }
//                is DataResult.Error -> hourlyWeather.error
//            }
//        }
//    }

//    private suspend fun getHourlyWeather(daysAmount: Int, dayWeather: DayWeather)
//            : DataResult<List<HourWeather>> {
//        return repository.getHourlyWeather(daysAmount, dayWeather)
//    }
}