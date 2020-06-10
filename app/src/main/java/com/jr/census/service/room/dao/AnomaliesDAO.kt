package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.Anomaly

@Dao
interface AnomaliesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(anomalies : List<Anomaly>)

    @Query("select * from anomalies")
    fun getAnomalies() : LiveData<List<Anomaly>>

}