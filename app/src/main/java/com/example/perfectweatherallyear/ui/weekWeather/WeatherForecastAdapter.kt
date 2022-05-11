package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(
    private val onItemClick: (DayWeather) -> Unit
) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    private val weekWeatherList: MutableList<DayWeather> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getDayWeather(position).also {
            viewHolder.bind(it)
        }
        viewHolder.itemView.setOnClickListener { onItemClick(weekWeatherList.get(position)) }
    }

    override fun getItemCount() = weekWeatherList.size

    fun setData(listDayWeather: List<DayWeather>) {
        weekWeatherList.clear()
        weekWeatherList.addAll(listDayWeather)
        notifyDataSetChanged()
    }

    private fun getDayWeather(position: Int): DayWeather {
        return weekWeatherList.get(position)
        }

    inner class ViewHolder(val binding: WeatherRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: DayWeather) {
            binding.dayWeather = weather
        }
    }
}

