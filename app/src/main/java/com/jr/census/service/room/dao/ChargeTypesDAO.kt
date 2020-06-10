package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.ChargeType
import com.jr.census.models.MeterBrand

@Dao
interface ChargeTypesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(types : List<ChargeType>)

    @Query("select * from charge_types")
    fun getChargeTypes() : LiveData<List<ChargeType>>

}