package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.databinding.WeatherRowItemBinding
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(private val dayOfWeekList: List<String>, private val weatherList: List<DayWeather>, onItemListener: ViewHolder.OnItemListener) :
        RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    private val mOnItemListener: ViewHolder.OnItemListener = onItemListener

    class ViewHolder(val binding: WeatherRowItemBinding, private val onItemListener: OnItemListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

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

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val binding = WeatherRowItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ViewHolder(binding, mOnItemListener)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.binding.dayOfWeekTextView.text = dayOfWeekList[position]
            viewHolder.binding.maxMinTempretureTextView.text = weatherList[position].temperature
        }

        override fun getItemCount() = dayOfWeekList.size
    }

