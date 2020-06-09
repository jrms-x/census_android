package com.jr.census.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jr.census.models.PropertyType

@Dao
interface PropertyTypesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(types : List<PropertyType>)
}