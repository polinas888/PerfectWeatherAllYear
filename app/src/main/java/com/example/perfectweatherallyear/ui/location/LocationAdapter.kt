package com.example.perfectweatherallyear.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.perfectweatherallyear.databinding.LocationItemBinding
import com.example.perfectweatherallyear.model.Location

class LocationAdapter(
    private val onItemClick: (Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    private val listLocations: MutableList<Location> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onItemClick)
    }

    fun setData(listLocation: List<Location>) {
        listLocations.clear()
        listLocations.addAll(listLocation)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getCity(position).also {
            viewHolder.bind(it)
        }

        viewHolder.itemView.setOnClickListener { onItemClick(listLocations.get(position)) }
    }

    override fun getItemCount() = listLocations.size

    private fun getCity(position: Int): Location {
        return listLocations[position]
    }

    inner class ViewHolder(private val binding: LocationItemBinding, val onClick: (Location) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            binding.location = location
        }
    }
}
