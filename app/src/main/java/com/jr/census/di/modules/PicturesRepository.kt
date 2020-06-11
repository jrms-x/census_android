package com.jr.census.di.modules

import androidx.lifecycle.LiveData
import com.jr.census.models.Picture
import com.jr.census.service.room.AppDatabase
import dagger.Module
import javax.inject.Inject

@Module
class PicturesRepository @Inject constructor(private val database : AppDatabase){

    fun getPictures(idProperty : Int) : LiveData<List<Picture>>{
        return database.pictures().getPictures(idProperty)
    }

    fun getLastPicture(idProperty: Int) : Picture?{
        return database.pictures().getLastPicture(idProperty)
    }

    fun insertPicture(picture: Picture){
        database.pictures().insert(picture)
    }

    fun deletePicture(picture: Picture){
        database.pictures().deletePicture(picture)
    }

    fun deletePictures(pictures : List<Picture>){
        database.pictures().deletePictures(pictures)
    }

    fun updatePicture(picture : Picture){
        database.pictures().updatePicture(picture)
    }

    fun updatePictures(pictures: List<Picture>){
        database.pictures().updatePictures(pictures)
    }
}