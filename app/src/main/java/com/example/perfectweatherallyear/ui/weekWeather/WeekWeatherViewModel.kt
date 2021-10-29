package com.example.perfectweatherallyear.ui.weekWeather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.Repository
import java.util.*

class WeekWeatherViewModel(val repository: Repository) : ViewModel() {
    val weekWeatherMapLiveData = MutableLiveData<Map<String, DayWeather>>()

    private val weekDaysList = Calendar.getInstance()
        .getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, Locale.ENGLISH)
        .toList().sortedBy { value -> value.second }.map { it.first }.toList()

    fun getDayWeather(weekDay: String): DataResult<List<DayWeather>> {
        var result: DataResult<List<DayWeather>>
        viewModelScope.launch {
            result = repository.getDayWeather(weekDay)
        }
        return result
    }

    var weekWeatherLiveData = MutableLiveData<List<DayWeather>>()

     fun getWeekWeather(city: String, daysAmount: Int): DataResult<List<DayWeather>> {
        var result: DataResult<List<DayWeather>>? = null
         viewModelScope.lauch {
             result = repository.getWeekWeather(city, daysAmount)
             when (result) {
                 is DataResult.Ok -> {
                     weekWeatherLiveData.postValue(result.response!!)
                 }
                 else -> {
                     Log.e("Error", "  ")
                 }
             }
         }
        return result
    }

//    fun getWeekWeather(): Result<List<DayWeather>>? {
//        var result: Result<List<DayWeather>>? = null
//        viewModelScope.launch {
//            result = repository.getWeekWeather()
//        }
//        return result
//    }

    private fun combineListsIntoOrderedMap(keys: List<String>, values: List<DayWeather>): Map<String, DayWeather> {
        require(keys.size == values.size) { "Cannot combine lists with dissimilar sizes" }
        val map: MutableMap<String, DayWeather> = LinkedHashMap()
        for (i in keys.indices) {
            map[keys[i]] = values[i]
        }
        return map
    }

    fun loadData() {
//        when (val weekWeather = getWeekWeather()) {
//            is Result.Error -> weekWeather.error
//            is Result.Ok -> {
//                val weekWeatherList = weekWeather.response
                val map: Map<String, DayWeather>? = weekWeatherLiveData.value?.let {
                    combineListsIntoOrderedMap(
                        weekDaysList, it
                    )
                }
                weekWeatherMapLiveData.value = map

            }
        }
//    }
//}
