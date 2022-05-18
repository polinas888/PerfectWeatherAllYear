package com.example.perfectweatherallyear.ui.detailWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.ItemHourWeatherBinding
import com.example.perfectweatherallyear.model.HourWeather

class DetailWeatherForecastAdapter
    : PagingDataAdapter<HourWeather, DetailWeatherForecastAdapter.WeatherViewHolder>(POST_COMPARATOR) {
    private val detailWeatherList: MutableList<HourWeather> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemHourWeatherBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: WeatherViewHolder, position: Int) {
        getDayWeather(position).also {
            viewHolder.bind(it)
        }
    }

    override fun getItemCount() = detailWeatherList.size

    fun setData(newData: List<HourWeather>) {
        detailWeatherList.clear()
        detailWeatherList.addAll(newData)
        notifyDataSetChanged()
    }

    private fun getDayWeather(position: Int): HourWeather {
        return detailWeatherList.get(position)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<HourWeather>() {

            override fun areContentsTheSame(oldItem: HourWeather, newItem: HourWeather): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: HourWeather, newItem: HourWeather): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class WeatherViewHolder(val binding: ItemHourWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hourWeather: HourWeather) {
            binding.hourWeather = hourWeather
        }
    }
}

