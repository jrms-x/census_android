package com.jr.census.di.modules

import com.jr.census.models.Login
import com.jr.census.models.LoginResponse
import dagger.Module
import retrofit2.Callback
import javax.inject.Inject

@Module
class LoginRepository @Inject constructor (private val api : ApiModule){

    fun getLogin(login : Login, callback : Callback<LoginResponse>){
        return api.getLogin(login).enqueue(callback)
    }

}