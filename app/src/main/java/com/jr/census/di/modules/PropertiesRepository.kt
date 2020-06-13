package com.jr.census.di.modules

import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.models.Property
import com.jr.census.models.PropertyCensusInformation
import com.jr.census.models.ServiceExecutionResponse
import com.jr.census.service.room.AppDatabase
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
class PropertiesRepository @Inject constructor (private val database : AppDatabase, private val api : ApiModule){

    fun getProperties(blockID : Int, propertiesCallback : ResponseServiceCallback<List<Property>>){
        api.getProperties(blockID)
            .enqueue(propertiesCallback)
    }

    fun getCensusData(id : Int, year : Int) : PropertyCensusInformation?{
        return database.propertyCensus().getCensus(id, year)
    }

    fun saveCensusData(censusData : PropertyCensusInformation,
                       responseServiceCallback: ResponseServiceCallback<ServiceExecutionResponse<Any>>){
        api.saveCensusData(censusData).enqueue(responseServiceCallback)

    }

    suspend fun getPropertiesFromDatabase(blockID: Int) : List<Property>
            = withContext(Dispatchers.IO){
        database.properties().getProperties(blockID)
    }

    suspend fun updateProperties(list: List<Property>,blockID: Int) = withContext(Dispatchers.IO){
        for (property  in list){
            launch {
                val updatedNumber = database.properties().update(property)
                if(updatedNumber <= 0){
                    database.properties().insert(property)
                }
                val census = property.census
                if(census != null){
                    database.propertyCensus().insert(census)
                }
            }
        }
        database.properties().deleteProperties(blockID, list.map{it.id})


    }

    fun saveCensusDataIntoDatabase(census: PropertyCensusInformation) {
        database.propertyCensus().insert(census)
    }
}