package com.example.perfectweatherallyear.ui.weekWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.model.DayWeather

class WeatherForecastAdapter(private val dayOfWeekList: List<String>, private val weatherList: List<DayWeather>, onItemListener: ViewHolder.OnItemListener) :
        RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    private val mOnItemListener: ViewHolder.OnItemListener = onItemListener

    class ViewHolder(view: View, private val onItemListener: OnItemListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val dayOfWeekTextView: TextView = view.findViewById(R.id.dayOfWeekTextView)
        val maxMinTemperatureTextView: TextView = view.findViewById(R.id.maxMinTempretureTextView)

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
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.weather_row_item, viewGroup, false)
            return ViewHolder(view, mOnItemListener)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.dayOfWeekTextView.text = dayOfWeekList[position]
            viewHolder.maxMinTemperatureTextView.text = weatherList[position].temperature
        }

        override fun getItemCount() = dayOfWeekList.size
    }

