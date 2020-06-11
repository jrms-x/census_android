package com.jr.census.service.room.dao

import androidx.room.*
import com.jr.census.models.Property

@Dao
interface PropertiesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(property : Property)

    @Update
    fun update(property: Property) : Int

    @Query("select * from properties where idBlock = :blockID")
    fun getProperties(blockID : Int) : List<Property>

    @Query("delete from properties where idBlock = :blockID and properties.id not in (:listIds)")
    fun deleteProperties(blockID: Int, listIds : List<Int>) : Int
}