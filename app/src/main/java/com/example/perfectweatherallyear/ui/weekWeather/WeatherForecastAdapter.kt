package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(
    private var weekWeather: List<DayWeather>,
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

    override fun getItemCount() = weekWeather.size

    fun setData(newData: List<DayWeather>) {
        weekWeather = newData
        notifyDataSetChanged()
    }

    private fun getDayWeather(position: Int): Pair<String, DayWeather> {
        return weekWeather[position].let {
            Pair(it.date, it)
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
            with(dayWeatherPair) {
                binding.dayWeather = second
                binding.weekDay = first
                currentDayWeather = second
            }
        }
    }
}

