package com.jr.census.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jr.census.R
import com.jr.census.databinding.ItemPicturesBinding
import com.jr.census.models.Picture
import com.squareup.picasso.Picasso

class PicturesAdapter(val context : Context)  : RecyclerView.Adapter<PicturesAdapter.PictureViewHolder>() {

    private var list : List<Picture> = listOf()

    fun setNewList(list : List<Picture>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.item_pictures, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.binding.setPicture(list[position])
        Picasso.get().load(list[position].location).into(holder.binding.picture)
        Picasso.get().load(list[position].location).into(holder.binding.pictureSmall)
    }

    class PictureViewHolder(val binding : ItemPicturesBinding) : RecyclerView.ViewHolder(binding.root)
}