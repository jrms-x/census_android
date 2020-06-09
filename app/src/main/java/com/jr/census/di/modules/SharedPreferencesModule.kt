package com.jr.census.di.modules

import android.app.Application
import com.jr.census.helpers.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class SharedPreferencesModule @Inject constructor(val application: Application ){

    private val sharedPreferencesHelper : SharedPreferencesHelper = SharedPreferencesHelper(application)


    @Singleton
    @Provides
    fun getSharedPreferencesHelper() : SharedPreferencesHelper{
        return sharedPreferencesHelper
    }
}