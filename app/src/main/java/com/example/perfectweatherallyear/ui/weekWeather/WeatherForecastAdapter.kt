package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(private val dayOfWeekList: List<String>, private val weatherList: List<DayWeather>, private val onClick: (DayWeather) -> Unit) :
        RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    class ViewHolder(view: View, val onClick: (DayWeather) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val dayOfWeekTextView: TextView = view.findViewById(R.id.dayOfWeekTextView)
        private val maxMinTemperatureTextView: TextView = view.findViewById(R.id.maxMinTempretureTextView)
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
            maxMinTemperatureTextView.text = dayWeather.temperature
        }

        override fun onClick(v: View?) { // do I need it here? Asks to implement
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.weather_row_item, viewGroup, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dayOfWeekTextView.text = dayOfWeekList[position]
        val dayWeather = getItem(position)
        viewHolder.bind(dayWeather)
    }

    override fun getItemCount() = dayOfWeekList.size

    private fun getItem(position: Int): DayWeather {
        return weatherList[position]
    }
}
