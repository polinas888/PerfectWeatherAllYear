package com.example.perfectweatherallyear.ui.detailWeather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.perfectweatherallyear.model.DayWeather
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.DataResult
import com.example.perfectweatherallyear.repository.WeatherRepository
import com.example.perfectweatherallyear.ui.weekWeather.DAYS_NUMBER
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailWeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    private val _detailWeatherFlowData = MutableSharedFlow<PagingData<HourWeather>>()
    val detailWeatherFlowData = _detailWeatherFlowData

//    private var pagingSource: PagingSource<Int, HourWeather>? = null

    lateinit var pager: Pager<Int, HourWeather>

//    fun loadData(dayWeather: DayWeather) {
//        viewModelScope.launch {
//
//            when (val hourlyWeather = repository.getHourlyWeatherPaged(DAYS_NUMBER, dayWeather)) {
//                is DataResult.Ok -> {
////                    pager = Pager(
////                        PagingConfig(pageSize = 6)
////                    ) {
////                        hourlyWeather.also {
////                            pagingSource = it.response
////                        }
////                    }.flow
//                    _detailWeatherFlowData.emit(hourlyWeather.response.last())
//                }
//                is DataResult.Error -> hourlyWeather.error
//            }
//        }
//    }

    fun loadData(dayWeather: DayWeather) {
        viewModelScope.launch {
            val data = repository.getHourlyWeatherPaged(DAYS_NUMBER, dayWeather)
            data.collectLatest { result ->
                when(result) {
                    is DataResult.Ok -> {
                        _detailWeatherFlowData.emit(result.response)
                    }
                    is DataResult.Error -> {
                        Log.d("wrong data", "wrong data")
                    }
                }
            }
        }
    }
}