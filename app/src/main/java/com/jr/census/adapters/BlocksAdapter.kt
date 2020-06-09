package com.jr.census.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jr.census.R
import com.jr.census.databinding.BlockListItemBinding
import com.jr.census.models.BlockPropertiesCount
import com.jr.census.view.callback.BlocksListListener

class BlocksAdapter(private val context : Context,
                    private var blocks : List<BlockPropertiesCount>,
                    private var blocksListListener: BlocksListListener
) : RecyclerView.Adapter<BlocksAdapter.BlockViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        val binding = DataBindingUtil.inflate<BlockListItemBinding>(
            LayoutInflater.from(context), R.layout.block_list_item, parent,  false
        )
        return BlockViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return blocks.size
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.binding.block = blocks[position]
        holder.binding.blockListener = blocksListListener
    }

    fun setList(list : List<BlockPropertiesCount>){
        blocks = list
        notifyDataSetChanged()
    }

    class BlockViewHolder(val binding : BlockListItemBinding) : RecyclerView.ViewHolder(binding.root)

}