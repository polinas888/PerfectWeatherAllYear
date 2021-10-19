package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(private val weekWeatherMap: Map<String, DayWeather>,
    onItemListener: ViewHolder.OnItemListener
) :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    private val mOnItemListener: ViewHolder.OnItemListener = onItemListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, mOnItemListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            dayOfWeekTextView.text = weekWeatherMap.keys.elementAt(position)
            maxMinTempretureTextView.text =   weekWeatherMap.get(weekWeatherMap.keys.toTypedArray()[position])?.temperature
        }
    }

    override fun getItemCount() = weekWeatherMap.size

    class ViewHolder(val binding: WeatherRowItemBinding, private val onItemListener: OnItemListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemListener.onItemClick(adapterPosition)
        }

        interface OnItemListener {
            fun onItemClick(position: Int)
        }
    }
}

