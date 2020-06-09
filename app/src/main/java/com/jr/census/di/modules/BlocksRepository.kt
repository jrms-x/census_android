package com.jr.census.di.modules

import androidx.lifecycle.LiveData
import com.jr.census.models.Block
import com.jr.census.models.BlockPropertiesCount
import com.jr.census.service.room.AppDatabase
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
class BlocksRepository @Inject constructor(private val database : AppDatabase){

    fun getBlocks() : LiveData<List<BlockPropertiesCount>> {
        return database.blocks().getBlocks()
    }

    suspend fun insertBlock(block : Block) = withContext(Dispatchers.IO){
        database.blocks().insert(block)
    }
}