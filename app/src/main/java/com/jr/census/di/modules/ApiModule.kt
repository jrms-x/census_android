package com.jr.census.di.modules

import android.app.Application
import com.jr.census.R
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.models.*
import com.jr.census.service.web.services.*
import dagger.Module
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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

    private val pictures : PictureService by lazy {
        retrofit.create(PictureService::class.java)
    }



    fun getAllCatalogs(): Call<CatalogsResponse> {
        return catalogs.getAll(getHeaderToken())
    }

    fun getProperties(blockID: Int): Call<List<Property>> {
        return properties.getList(blockID, getHeaderToken())
    }

    fun saveCensusData(propertyCensusInformation: PropertyCensusInformation): Call<ServiceExecutionResponse<Any>>{
        return properties.sendCensusData(getHeaderToken(), propertyCensusInformation)
    }

    fun getLogin(login: Login): Call<LoginResponse> {
        return this.login.login(login)
    }

    fun getRegister(register: Register): Call<RegisterResponse> {
        return this.register.register(register)
    }

    fun getPictures(id : Int, year : Int) : Call<List<Picture>>{
        return pictures.getPictures(getHeaderToken(), id, year)
    }

    fun deletePicture(picture: Picture) : Call<ServiceExecutionResponse<Any?>?>{
        return pictures.deletePicture(getHeaderToken(), picture)
    }

    fun addPicture(picture: Picture) : Call<ServiceExecutionResponse<String?>?>{
        val file = File(picture.location)
        val body = RequestBody.create(MediaType.get("image/*"), file)
        val partFile = MultipartBody.Part.createFormData("image", file.name, body)
        return pictures.addPicture(getHeaderToken(), partFile, picture.propertyID, picture.title!!,
            picture.subtitle, picture.description, picture.order, picture.year
        )
    }

    fun downloadPicture(blobId : String) : Call<Any?>{
        return pictures.getPictureFile(getHeaderToken(), blobId)
    }

    fun updateProperty(property: Property): Call<ServiceExecutionResponse<Any?>?> {
        return properties.updateProperty(getHeaderToken(), property)
    }

    private fun getHeaderToken(): String {
        return "bearer " + sharedPreferences.getToken()
    }


}