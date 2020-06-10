package com.jr.census.di.modules

import androidx.lifecycle.LiveData
import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.models.*
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

    fun getChargeTypes() : LiveData<List<ChargeType>>{
        return database.chargeTypes().getChargeTypes()
    }

    fun getMeterBrands() : LiveData<List<MeterBrand>>{
        return database.meterBrands().getMeterBrands()
    }

    fun getMeterStatus() : LiveData<List<MeterStatus>>{
        return database.meterStatus().getMeterStatus()
    }

    fun getOutletTypes() : LiveData<List<OutletType>>{
        return database.outletTypes().getOutletTypes()
    }

    fun getPropertyTypes() : LiveData<List<PropertyType>>{
        return database.propertyTypes().getPropertiesTypes()
    }

    fun getProtectionTypes() : LiveData<List<ProtectionType>>{
        return database.protectionTypes().getProtectionTypes()
    }

    fun getAnomalies() : LiveData<List<Anomaly>>{
        return database.anomalies().getAnomalies()
    }
}