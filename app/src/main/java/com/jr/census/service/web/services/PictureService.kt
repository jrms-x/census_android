package com.jr.census.service.web.services

import com.jr.census.models.Picture
import com.jr.census.models.ServiceExecutionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PictureService {
    @GET("properties/{idProperty}/{year}/pictures")
    fun getPictures(@Header("Authorization") Authorization : String,
        @Path("idProperty") idProperty : Int, @Path("year") year : Int):Call<List<Picture>>

    @HTTP(method = "DELETE", path = "pictures", hasBody = true)
    fun deletePicture(@Header("Authorization") Authorization : String,
                      @Body picture: Picture) : Call<ServiceExecutionResponse<Any?>?>

    @POST("pictures")
    @Multipart
    fun addPicture(@Header("Authorization") Authorization : String,
                   @Part   picture : MultipartBody.Part,
                   @Part("id_property") idProperty: Int,
                   @Part("title") title: String,
                   @Part("subtitle") subtitle: String?,
                   @Part("description") description: String?,
                   @Part("order") order: Int,
                   @Part("year") year : Int
    ) : Call<ServiceExecutionResponse<String?>?>

    @GET("pictures")
    fun getPictureFile(@Header("Authorization") Authorization : String,
                       @Query("id") id : String) : Call<Any?>

}