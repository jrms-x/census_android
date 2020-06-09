package com.jr.census.di.modules

import android.app.Application
import com.jr.census.R
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.models.*
import com.jr.census.service.web.services.CatalogsService
import com.jr.census.service.web.services.LoginService
import com.jr.census.service.web.services.PropertyService
import com.jr.census.service.web.services.RegisterService
import dagger.Module
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@Module
class ApiModule @Inject constructor(
    application: Application,
    private val sharedPreferences: SharedPreferencesHelper
) {

    private val retrofit = Retrofit.Builder().baseUrl(application.getString(R.string.url))
        .addConverterFactory(GsonConverterFactory.create()).build()


    private val login: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    private val register: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

    private val catalogs: CatalogsService by lazy {
        retrofit.create(CatalogsService::class.java)
    }

    private val properties: PropertyService by lazy {
        retrofit.create(PropertyService::class.java)
    }


    fun getAllCatalogs(): Call<CatalogsResponse> {
        return catalogs.getAll(getHeaderToken())
    }

    fun getProperties(blockID: Int): Call<List<Property>> {
        return properties.getList(blockID, getHeaderToken())
    }

    fun getLogin(login: Login): Call<LoginResponse> {
        return this.login.login(login)
    }

    fun getRegister(register: Register): Call<RegisterResponse> {
        return this.register.register(register)
    }

    private fun getHeaderToken(): String {
        return "bearer " + sharedPreferences.getToken()
    }
}