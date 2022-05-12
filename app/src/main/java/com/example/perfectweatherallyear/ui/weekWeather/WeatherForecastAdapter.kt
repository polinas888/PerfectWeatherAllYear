package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(
    private var weekWeatherList: List<DayWeather>,
    private var listener: OnItemClick,
    private var parentFragmentManager: FragmentManager
) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getDayWeather(position).also {
            viewHolder.bind(it)
        }
    }

    override fun getItemCount() = weekWeatherList.size

    fun setData(newData: List<DayWeather>) {
        weekWeatherList = newData
        notifyDataSetChanged()
    }

    private fun getDayWeather(position: Int): DayWeather {
        return weekWeatherList.get(position)
        }

    inner class ViewHolder(private val binding: WeatherRowItemBinding, listener: OnItemClick) : RecyclerView.ViewHolder(binding.root) {
        private var currentWeather: DayWeather? = null

        init {
            itemView.setOnClickListener {
                currentWeather?.let { dayWeather -> listener.onItemClick(dayWeather, parentFragmentManager) }
            }
        }

        fun bind(weather: DayWeather) {
            binding.dayWeather = weather
            currentWeather = weather
        }
    }

    interface OnItemClick {
        fun onItemClick(dayWeather: DayWeather, parentFragmentManager: FragmentManager)
    }
}

