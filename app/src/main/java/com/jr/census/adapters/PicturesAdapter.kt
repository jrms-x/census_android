package com.jr.census.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.net.Uri
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.set
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jr.census.R
import com.jr.census.databinding.ItemPicturesBinding
import com.jr.census.models.Picture
import com.jr.census.view.callback.ImageListListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pictures.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class PicturesAdapter(private val context : Context,
                      private val imageListener : ImageListListener,
                      private val scope : CoroutineScope)  : RecyclerView.Adapter<PicturesAdapter.PictureViewHolder>() {

    private var list : List<Picture> = listOf()
    private var sparseSelected : SparseArray<Boolean> = SparseArray()

    fun setNewList(list : List<Picture>){
        this.list = list
        notifyDataSetChanged()
    }

    fun deselectAll(){
        sparseSelected.clear()
        notifyDataSetChanged()
    }

    fun getAllSelected() : List<Picture>{
        return list.filterIndexed {i, _ -> sparseSelected[i] == true  }
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
        holder.binding.listener = imageListener
        if(sparseSelected[position] == true){
            setSelectedImageColor(holder.binding.root.picture)
        }else{
            holder.binding.root.picture.colorFilter = null
        }
        holder.binding.root.picture.setOnLongClickListener{
            if(!imageListener.isSelectMode()){
                imageListener.startSelection()
            }
            setSelectedImageColor(holder.binding.root.picture)
            sparseSelected[position] = true

            true
        }
        holder.binding.root.picture.setOnClickListener {
            if(imageListener.isSelectMode()){
                if(sparseSelected[position] != true){
                    setSelectedImageColor(holder.binding.root.picture)
                    sparseSelected[position] = true
                }else{
                    holder.binding.root.picture.colorFilter = null
                    sparseSelected.remove(position)
                }
                if(sparseSelected.size() <= 0){
                    imageListener.finishSelection()
                }

            }else{
                imageListener.onSelectImage(list[position])
            }
        }
        holder.binding.root.pictureSmall.setOnClickListener {
            imageListener.onSelectImage(list[position])
        }
        scope.launch {
            val location : Uri = withContext(Dispatchers.IO){
                val file = File(list[position].location)
                if(file.exists()){
                    Uri.fromFile(file)
                }else{
                    Uri.parse(list[position].location)
                }
            }
            Picasso.get().load(location).into(holder.binding.picture)
            Picasso.get().load(location).into(holder.binding.pictureSmall)
        }


    }

    private fun setSelectedImageColor(imageView : ImageView){
       imageView.setColorFilter(ResourcesCompat.
        getColor(context.resources,R.color.colorAccent, null),
            PorterDuff.Mode.MULTIPLY)
    }

    class PictureViewHolder(val binding : ItemPicturesBinding) : RecyclerView.ViewHolder(binding.root)
}