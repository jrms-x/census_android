package com.jr.census.di.modules

import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.models.Property
import com.jr.census.service.room.AppDatabase
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
class PropertiesRepository @Inject constructor (val database : AppDatabase, val api : ApiModule){

    fun getProperties(blockID : Int, propertiesCallback : ResponseServiceCallback<List<Property>>){
        api.getProperties(blockID)
            .enqueue(propertiesCallback)
    }

    suspend fun getPropertiesFromDatabase(blockID: Int) : List<Property>
            = withContext(Dispatchers.IO){
        database.properties().getProperties(blockID)
    }

    suspend fun updateProperties(list: List<Property>,blockID: Int) = withContext(Dispatchers.IO){
        database.properties().deleteProperties(blockID)
        database.properties().insert(list)
    }
}