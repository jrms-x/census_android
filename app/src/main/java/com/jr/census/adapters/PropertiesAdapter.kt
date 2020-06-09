package com.jr.census.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jr.census.R
import com.jr.census.databinding.PropertyListItemBinding
import com.jr.census.models.Property
import com.jr.census.view.callback.PropertyListListener

class PropertiesAdapter(private val context : Context,
                        private var properties : List<Property>,
                        private val propertyListListener: PropertyListListener) : RecyclerView.Adapter<PropertiesAdapter.PropertyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = DataBindingUtil.inflate<PropertyListItemBinding>(LayoutInflater.from(context),
        R.layout.property_list_item, parent, false)
        return PropertyViewHolder(binding.root, binding)
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    fun setElements(elements : List<Property>){
        properties = elements
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.binding.property = properties[position]
        holder.binding.listener = propertyListListener
    }

    class PropertyViewHolder(view : View, val binding: PropertyListItemBinding) : RecyclerView.ViewHolder(view)
}