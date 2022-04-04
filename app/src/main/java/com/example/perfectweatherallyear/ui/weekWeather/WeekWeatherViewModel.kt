package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perfectweatherallyear.model.DayWeather
import java.util.*

class WeekWeatherViewModel : ViewModel() {
    val weekWeatherMapLiveData = MutableLiveData<Map<String,DayWeather>>()

    private val weekDaysList = Calendar.getInstance()
        .getDisplayNames(Calendar.DAY_OF_WEEK,Calendar.LONG_FORMAT,Locale.ENGLISH)
        .toList().sortedBy { value -> value.second }.map { it.first}.toList()

    private fun combineListsIntoOrderedMap(keys: List<String>, values: List<DayWeather>): Map<String, DayWeather> {
        require(keys.size == values.size) { "Cannot combine lists with dissimilar sizes" }
        val map: MutableMap<String, DayWeather> = LinkedHashMap()
        for (i in keys.indices) {
            map[keys[i]] = values[i]
        }
        return map
    }
}
