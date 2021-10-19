package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(private val dayOfWeekList: List<String>, private val weatherList: List<DayWeather>, private val onClick: (DayWeather) -> Unit) :
        RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    class ViewHolder(val binding: WeatherRowItemBinding, val onClick: (DayWeather) -> Unit) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

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

        override fun onClick(v: View?) { // do I need it here? Asks to implement
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dayWeather = getItem(position)
        viewHolder.binding.dayOfWeekTextView.text = dayOfWeekList[position]
        viewHolder.binding.maxMinTempretureTextView.text = dayWeather.temperature
        viewHolder.bind(dayWeather)
    }

    override fun getItemCount() = dayOfWeekList.size

    private fun getItem(position: Int): DayWeather {
        return weatherList[position]
    }
}
