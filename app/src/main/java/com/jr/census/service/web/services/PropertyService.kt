package com.jr.census.service.web.services

import com.jr.census.models.Property
import com.jr.census.models.PropertyCensusInformation
import com.jr.census.models.ServiceExecutionResponse
import retrofit2.Call
import retrofit2.http.*

interface PropertyService {
    @GET("blocks/{block}/properties")
    fun getList(@Path("block") block : Int,
                @Header("Authorization") authorization : String ) : Call<List<Property>>

    @POST("censusData/")
    fun sendCensusData(@Header("Authorization") authorization :
                       String, @Body censusData : PropertyCensusInformation) : Call<ServiceExecutionResponse<Any>>

    @PUT("properties")
    fun updateProperty(@Header("Authorization")token: String, @Body property: Property): Call<ServiceExecutionResponse<Any?>?>
}