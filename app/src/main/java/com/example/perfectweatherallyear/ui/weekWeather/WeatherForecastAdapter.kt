package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(private val weekWeatherMap: Map<String, DayWeather>, private val onClick: (DayWeather) -> Unit)
    : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    var dayWeather: DayWeather? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            dayWeather = weekWeatherMap.get(weekWeatherMap.keys.toTypedArray()[position])
            dayOfWeekTextView.text = weekWeatherMap.keys.elementAt(position)
            maxMinTempretureTextView.text =   dayWeather?.temperature
        }
        dayWeather?.let { viewHolder.bind(it) }
    }

    override fun getItemCount() = weekWeatherMap.size

    inner class ViewHolder(val binding: WeatherRowItemBinding, val onClick: (DayWeather) -> Unit) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private var currentDayWeather: DayWeather? = null

        init {
            itemView.setOnClickListener {
                currentDayWeather?.let {
                    onClick(it)
                }
            }
        }

        fun bind(dayWeather: DayWeather) {
            currentDayWeather = dayWeather
        }

        override fun onClick(view: View?) {
        }
    }
}

