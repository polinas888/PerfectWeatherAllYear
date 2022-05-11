package com.example.perfectweatherallyear.ui.detailWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.ItemHourWeatherBinding
import com.example.perfectweatherallyear.model.HourWeather

class DetailWeatherForecastAdapter(
) : RecyclerView.Adapter<DetailWeatherForecastAdapter.ViewHolder>() {
    private val detailWeatherList: MutableList<HourWeather> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHourWeatherBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
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

    inner class ViewHolder(val binding: ItemHourWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hourWeather: HourWeather) {
            binding.hourWeather = hourWeather
        }
    }
}

