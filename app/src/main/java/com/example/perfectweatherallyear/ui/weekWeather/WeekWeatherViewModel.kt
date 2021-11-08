package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepositoryImp
import kotlinx.coroutines.launch

class WeekWeatherViewModel(val repository: WeatherRepositoryImp) : ViewModel() {
    val weekWeatherLiveData = MutableLiveData<List<DayWeather>>()

    fun loadData() {
        viewModelScope.launch {
            when (val weekWeather = getWeekWeather("London", 7)) {
                is DataResult.Ok -> {
                    weekWeatherLiveData.value = weekWeather.response!!
                }
                is DataResult.Error -> weekWeather.error
            }
        }
    }

    private suspend fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        return repository.getWeekWeather(city, daysAmount)
    }
}

