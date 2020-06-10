package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.MeterBrand
import com.jr.census.models.MeterStatus
import com.jr.census.models.PropertyType

@Dao
interface PropertyTypesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(types : List<PropertyType>)

    @Query("select * from property_types")
    fun getPropertiesTypes() : LiveData<List<PropertyType>>

}