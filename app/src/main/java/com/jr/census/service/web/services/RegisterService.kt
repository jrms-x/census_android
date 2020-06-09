package com.jr.census.service.web.services

import com.jr.census.models.Register
import com.jr.census.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    fun register(@Body register: Register) : Call<RegisterResponse>
}