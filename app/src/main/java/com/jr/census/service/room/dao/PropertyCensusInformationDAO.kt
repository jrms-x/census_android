package com.jr.census.service.room.dao

import androidx.room.*
import com.jr.census.models.PropertyCensusInformation

@Dao
interface PropertyCensusInformationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(propertyCensusInformation: PropertyCensusInformation)

    @Update
    fun update(propertyCensusInformation: PropertyCensusInformation)

    @Query("select * from census_data where id_property = :idProperty and year = :year limit 1")
    fun getCensus(idProperty : Int, year : Int) : PropertyCensusInformation?
}