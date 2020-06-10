package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.MeterBrand

@Dao
interface MeterBrandsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(brands : List<MeterBrand>)

    @Query("select * from meter_brands")
    fun getMeterBrands() : LiveData<List<MeterBrand>>
}