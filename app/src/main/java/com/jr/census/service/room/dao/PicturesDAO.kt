package com.jr.census.service.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jr.census.models.Picture

@Dao
interface PicturesDAO {
    @Insert
    fun insert(picture : Picture)

    @Update
    fun updatePicture(picture: Picture)

    @Update
    fun updatePictures(pictures: List<Picture>)

    @Query("select * from pictures where propertyID = :idProperty order by `order`, year")
    fun getPictures(idProperty : Int) : LiveData<List<Picture>>

    @Delete
    fun deletePicture(picture: Picture)

    @Delete
    fun deletePictures(pictures : List<Picture>)

    @Query("select * from pictures where propertyID = :idProperty order by `order`, year desc limit 1")
    fun getLastPicture(idProperty: Int): Picture?

    @Query("select * from pictures where id = :id limit 1")
    fun getPicture(id: Int): Picture?
}