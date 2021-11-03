package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.Repository
import kotlinx.coroutines.launch
import java.util.*

class WeekWeatherViewModel(val repository: Repository) : ViewModel() {
    val weekWeatherMapLiveData = MutableLiveData<Map<String, DayWeather>>()

    private val weekDaysList = Calendar.getInstance()
        .getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, Locale.ENGLISH)
        .toList().sortedBy { value -> value.second }.map { it.first }.toList()

    fun loadData() {
        viewModelScope.launch {
            when (val weekWeather = getWeekWeather("London", 1)) {
                is DataResult.Ok -> {
                    val map: Map<String, DayWeather> =
                        combineListsIntoOrderedMap(weekDaysList, weekWeather.response)
                    weekWeatherMapLiveData.value = map
                }
                is DataResult.Error -> weekWeather.error
            }
        }
    }

    private suspend fun getWeekWeather(
        city: String,
        daysAmount: Int
    ): DataResult<List<DayWeather>> {
        return repository.getWeekWeather(city, daysAmount)
    }

    private fun combineListsIntoOrderedMap(
        keys: List<String>,
        values: List<DayWeather>
    ): Map<String, DayWeather> {
        require(keys.size == values.size) { "Cannot combine lists with dissimilar sizes" }
        val map: MutableMap<String, DayWeather> = LinkedHashMap()
        for (i in keys.indices) {
            map[keys[i]] = values[i]
        }
        return map
    }
}

