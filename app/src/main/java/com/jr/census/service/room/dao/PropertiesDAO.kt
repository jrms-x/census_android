package com.jr.census.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jr.census.models.Property

@Dao
interface PropertiesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(properties : List<Property>)

    @Query("select * from properties where idBlock = :blockID")
    fun getProperties(blockID : Int) : List<Property>

    @Query("delete from properties where idBlock = :blockID")
    fun deleteProperties(blockID: Int) : Int
}