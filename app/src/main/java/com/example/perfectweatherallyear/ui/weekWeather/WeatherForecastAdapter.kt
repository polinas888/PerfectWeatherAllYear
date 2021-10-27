package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(
    private var weekWeatherMap: Map<String, DayWeather>,
    private val onItemClick: (DayWeather) -> Unit
) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            getDayWeather(position).also {
                viewHolder.bind(it)
            }
    }

    override fun getItemCount() = weekWeatherMap.size

    fun setData(newData: Map<String, DayWeather>) {
         weekWeatherMap = newData
        notifyDataSetChanged()
    }

    private fun getDayWeather(position: Int): Pair<String, DayWeather> {
        return weekWeatherMap.entries.toTypedArray()[position].let {
            Pair(it.key, it.value)
        }
    }

    inner class ViewHolder(
        val binding: WeatherRowItemBinding,
        val onClick: (DayWeather) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentDayWeather: DayWeather? = null

        init {
            itemView.setOnClickListener {
                currentDayWeather?.let {
                    onClick(it)
                }
            }
        }

        fun bind(dayWeatherPair: Pair<String, DayWeather>) {
            binding.dayWeather = dayWeatherPair.second
            binding.weekDay = dayWeatherPair.first
            currentDayWeather = dayWeatherPair.second
        }
    }
}

