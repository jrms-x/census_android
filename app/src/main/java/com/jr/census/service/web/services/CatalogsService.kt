package com.jr.census.service.web.services


import com.jr.census.models.CatalogsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CatalogsService {
    @GET("catalogs")
    fun getAll(@Header("Authorization") Authorization : String) : Call<CatalogsResponse>
}