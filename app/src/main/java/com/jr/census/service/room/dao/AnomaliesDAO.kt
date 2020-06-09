package com.jr.census.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jr.census.models.Anomaly

@Dao
interface AnomaliesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(anomalies : List<Anomaly>)



}