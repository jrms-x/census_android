package com.jr.census.di.modules

import androidx.lifecycle.LiveData
import com.jr.census.models.Picture
import com.jr.census.models.Property
import com.jr.census.models.ServiceExecutionResponse
import com.jr.census.service.room.AppDatabase
import dagger.Module
import retrofit2.Callback
import javax.inject.Inject

@Module
class PicturesRepository @Inject constructor(private val database : AppDatabase, private val apiModule: ApiModule){

    fun getPictures(idProperty : Int) : LiveData<List<Picture>>{
        return database.pictures().getPictures(idProperty)
    }

    fun getPicture(id : Int) : Picture?{
        return database.pictures().getPicture(id)
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

    fun getPictures(id : Int, year : Int, callback: Callback<List<Picture>>){
        apiModule.getPictures(id, year).enqueue(callback)
    }

    fun addPicture(picture: Picture, callback: Callback<ServiceExecutionResponse<String?>?>){
        apiModule.addPicture(picture).enqueue(callback)
    }

    fun deletePicture(picture: Picture, callback: Callback<ServiceExecutionResponse<Any?>?>){
        apiModule.deletePicture(picture).enqueue(callback)
    }

    fun downloadPicture(blobId : String, callback : Callback<Any?>){
        apiModule.downloadPicture(blobId).enqueue(callback)
    }

    fun updateProperty(property: Property, callback: Callback<ServiceExecutionResponse<Any?>?>){
        apiModule.updateProperty(property).enqueue(callback)
    }
}