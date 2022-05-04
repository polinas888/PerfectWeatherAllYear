package com.example.perfectweatherallyear.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.LocationItemBinding
import com.example.perfectweatherallyear.model.Location

class LocationAdapter(
    private var locationsList: List<Location>,
    private val onItemClick: (Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getCity(position).also {
            viewHolder.bind(it)
        }

        viewHolder.itemView.setOnClickListener { onItemClick(locationsList.get(position)) }
    }

    override fun getItemCount() = locationsList.size

    private fun getCity(position: Int): Location {
        return locationsList[position]
    }

    inner class ViewHolder(private val binding: LocationItemBinding, val onClick: (Location) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.location = location
        }
    }
}
