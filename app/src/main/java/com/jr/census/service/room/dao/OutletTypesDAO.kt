package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.MeterBrand
import com.jr.census.models.MeterStatus
import com.jr.census.models.OutletType

@Dao
interface OutletTypesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(types : List<OutletType>)

    @Query("select * from outlet_types")
    fun getOutletTypes() : LiveData<List<OutletType>>
}