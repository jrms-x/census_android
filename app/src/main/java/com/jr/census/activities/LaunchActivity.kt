package com.jr.census.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.di.modules.CatalogsRepository
import com.jr.census.helpers.OnResultFromWebService
import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.models.CatalogsResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchActivity : AppCompatActivity(), OnResultFromWebService<CatalogsResponse> {

    @Inject
    lateinit var catalogsRepository: CatalogsRepository

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as CensusApplication).appComponent.inject(this)
        val catalogsCallback = ResponseServiceCallback(this, this)
        setContentView(R.layout.activity_launch)
        catalogsRepository.getCatalogs(catalogsCallback)
    }

    override fun onSuccess(result: CatalogsResponse?, statusCode : Int) {
        if(result != null){
            MainScope().launch {
                catalogsRepository.saveCatalogs(result)
            }
        }else{
            Log.d("CatalogsResponse", "Result from service is null")
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onFailed(t : Throwable?) {
        Log.e("catalogs", "Connection to server failed", t)
        val classActivity : Class<out Activity> =
        if(sharedPreferencesHelper.getToken() == null){
            LoginActivity::class.java
        }else{
            MainActivity::class.java
        }
        val intent = Intent(this, classActivity)
        startActivity(intent)
        finish()
    }


}
