package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.MeterBrand
import com.jr.census.models.MeterStatus
import com.jr.census.models.ProtectionType

@Dao
interface ProtectionTypesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(types : List<ProtectionType>)

    @Query("select * from protection_types")
    fun getProtectionTypes() : LiveData<List<ProtectionType>>

}