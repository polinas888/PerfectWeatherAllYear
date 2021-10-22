package com.example.perfectweatherallyear.ui.weekWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.WeekDay
import com.example.perfectweatherallyear.repository.WeatherData.WeatherRepository
import com.example.perfectweatherallyear.repository.WeatherData.WeatherRepositoryImp
import com.example.perfectweatherallyear.repository.WeatherData.WeatherResult
import kotlinx.coroutines.launch
import java.util.*

class WeekWeatherViewModel : ViewModel() {
    private val weatherRepository: WeatherRepository = WeatherRepositoryImp()
    val weekDaysList = listOf(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY,
        WeekDay.THURSDAY, WeekDay.FRIDAY, WeekDay.SATURDAY, WeekDay.SUNDAY)

    fun getDayWeather(weekDay: WeekDay): WeatherResult? {
        var result: WeatherResult? = null
        viewModelScope.launch {
            try {
                result = WeatherResult.Ok(weatherRepository.getDayWeather(weekDay))
            } catch (ex: Exception) {
                result = WeatherResult.Error(ex.message)
            }
        }
        return result
    }

    fun getWeekWeather(): WeatherResult? {
        var result: WeatherResult? = null
        viewModelScope.launch {
            try {
                result = WeatherResult.Ok(weatherRepository.getWeekWeather())
            } catch (ex: Exception) {
                result = WeatherResult.Error(ex.message)
            }
        }
        return result
    }

    fun combineListsIntoOrderedMap(keys: List<WeekDay>, values: List<DayWeather>): Map<WeekDay, DayWeather> {
        require(keys.size == values.size) { "Cannot combine lists with dissimilar sizes" }
        val map: MutableMap<WeekDay, DayWeather> = LinkedHashMap()
        for (i in keys.indices) {
            map[keys[i]] = values[i] }
        return map
    }
}
