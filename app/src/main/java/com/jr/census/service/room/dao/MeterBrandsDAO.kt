package com.jr.census.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jr.census.models.MeterBrand

@Dao
interface MeterBrandsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(brands : List<MeterBrand>)
}