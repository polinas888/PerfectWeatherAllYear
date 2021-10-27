package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.Result
import com.example.perfectweatherallyear.repository.WeatherData.WeatherRepository
import com.example.perfectweatherallyear.repository.WeatherData.WeatherRepositoryImp
import kotlinx.coroutines.launch
import java.util.*

class WeekWeatherViewModel : ViewModel() {
    private val weatherRepository: WeatherRepository = WeatherRepositoryImp()

    val weekDaysList = Calendar.getInstance()
        .getDisplayNames(Calendar.DAY_OF_WEEK,Calendar.LONG_FORMAT,Locale.ENGLISH)
        .toList().sortedBy { value -> value.second }.map { it.first}.toList()

    fun getDayWeather(weekDay: String): Result<List<DayWeather>>? {
        var result: Result<List<DayWeather>>? = null
        viewModelScope.launch {
            result = weatherRepository.getDayWeather(weekDay)
        }
        return result
    }

    fun getWeekWeather(): Result<List<DayWeather>>? {
        var result: Result<List<DayWeather>>? = null
        viewModelScope.launch {
            result = weatherRepository.getWeekWeather()
        }
        return result
    }

    fun combineListsIntoOrderedMap(keys: List<String>, values: List<DayWeather>): Map<String, DayWeather> {
        require(keys.size == values.size) { "Cannot combine lists with dissimilar sizes" }
        val map: MutableMap<String, DayWeather> = LinkedHashMap()
        for (i in keys.indices) {
            map[keys[i]] = values[i]
        }
        return map
    }
}
