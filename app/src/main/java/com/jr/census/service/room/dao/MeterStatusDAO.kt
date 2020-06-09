package com.jr.census.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jr.census.models.MeterStatus

@Dao
interface MeterStatusDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(status : List<MeterStatus>)
}