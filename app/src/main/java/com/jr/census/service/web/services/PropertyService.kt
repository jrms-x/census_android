package com.jr.census.service.web.services

import com.jr.census.models.Property
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PropertyService {
    @GET("blocks/{block}/properties")
    fun getList(@Path("block") block : Int,
                @Header("Authorization") authorization : String ) : Call<List<Property>>
}