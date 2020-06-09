package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.Block
import com.jr.census.models.BlockPropertiesCount

@Dao
interface BlocksDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(block : Block)

    @Query("select blocks.id, count(properties.id) as propertiesCount from blocks, properties where blocks.id = properties.idBlock group by blocks.id")
    fun getBlocks() : LiveData<List<BlockPropertiesCount>>
}