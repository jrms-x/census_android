package com.jr.census.di.modules

import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.models.CatalogsResponse
import com.jr.census.service.room.AppDatabase
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
class CatalogsRepository @Inject constructor(private val apiModule: ApiModule, private val database: AppDatabase){

    fun getCatalogs(responseServiceCallback: ResponseServiceCallback<CatalogsResponse>){
        apiModule.getAllCatalogs().enqueue(responseServiceCallback)
    }

    suspend fun saveCatalogs(response : CatalogsResponse) = withContext(Dispatchers.IO){
        database.saveCatalogs(response)
    }
}