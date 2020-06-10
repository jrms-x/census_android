package com.jr.census.service.room


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jr.census.models.*
import com.jr.census.service.room.dao.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Database(entities = [Anomaly::class, Block::class ,ChargeType::class, OutletType::class, MeterBrand::class,
MeterStatus::class, Property::class ,PropertyType::class, ProtectionType::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun anomalies() : AnomaliesDAO
    abstract fun chargeTypes() : ChargeTypesDAO
    abstract fun outletTypes () : OutletTypesDAO
    abstract fun meterBrands() : MeterBrandsDAO
    abstract fun meterStatus() : MeterStatusDAO
    abstract fun propertyTypes() : PropertyTypesDAO
    abstract fun protectionTypes() : ProtectionTypesDAO
    abstract fun properties() : PropertiesDAO
    abstract fun blocks() : BlocksDAO

    companion object{
        @Volatile
        private var instance : AppDatabase? = null
        fun getInstance(context : Context) : AppDatabase{
            return if(instance != null){
                return instance!!
            }else{
                synchronized(this){
                    if(instance == null){
                        instance = Room.databaseBuilder(context, AppDatabase::class.java,
                            "aquacensus.db").build()
                    }
                    instance!!
                }
            }
        }
    }

    suspend fun saveCatalogs(catalogs : CatalogsResponse) = withContext(Dispatchers.IO){
        val anomalies = catalogs.anomalies
        val chargeTypes = catalogs.typesCharges
        val intakeTypes = catalogs.typesOutlets
        val meterBrands = catalogs.meterBrands
        val meterStatus = catalogs.meterStatus
        val propertyTypes = catalogs.typesProperties
        val protectionTypes = catalogs.typesProtections
        if(anomalies != null){
            anomalies().insert(anomalies)
        }
        if(chargeTypes != null){
            chargeTypes().insert(chargeTypes)
        }
        if(intakeTypes != null){
            outletTypes().insert(intakeTypes)
        }
        if(meterBrands != null){
            meterBrands().insert(meterBrands)
        }
        if(meterStatus != null){
            meterStatus().insert(meterStatus)
        }
        if(propertyTypes != null){
            propertyTypes().insert(propertyTypes)
        }
        if(protectionTypes != null){
            protectionTypes().insert(protectionTypes)
        }
        Log.d("database", "Saved catalogs")
    }
}