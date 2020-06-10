package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.MeterBrand
import com.jr.census.models.MeterStatus

@Dao
interface MeterStatusDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(status : List<MeterStatus>)

    @Query("select * from meter_status")
    fun getMeterStatus() : LiveData<List<MeterStatus>>
}