package com.jr.census.service.web.services

import com.jr.census.models.Login
import com.jr.census.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun login(@Body login : Login) : Call<LoginResponse>
}