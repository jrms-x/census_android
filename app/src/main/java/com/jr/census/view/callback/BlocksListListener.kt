package com.jr.census.view.callback

import com.jr.census.models.BlockPropertiesCount

interface BlocksListListener {
    fun onItemClick(block : BlockPropertiesCount)
}