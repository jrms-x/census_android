package com.jr.census.di.modules

import com.jr.census.models.Register
import com.jr.census.models.RegisterResponse
import dagger.Module
import retrofit2.Callback
import javax.inject.Inject

@Module
class RegisterRepository @Inject constructor (private val api : ApiModule){

    fun getRegister(register: Register, callback: Callback<RegisterResponse>) {
        return api.getRegister(register).enqueue(callback)
    }
}