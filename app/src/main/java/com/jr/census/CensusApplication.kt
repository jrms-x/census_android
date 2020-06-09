package com.jr.census

import android.app.Application
import com.jr.census.BuildConfig
import com.jr.census.di.AppComponent
import com.jr.census.di.DaggerAppComponent
import com.jr.census.di.modules.ApplicationModule
import com.jr.census.di.modules.DatabaseModule
import com.jr.census.di.modules.SharedPreferencesModule
import com.facebook.stetho.Stetho
import dagger.Module

@Module
class CensusApplication : Application() {

    val appComponent: AppComponent by lazy{
        DaggerAppComponent.builder().
        applicationModule(ApplicationModule(this)).
        databaseModule(DatabaseModule(this)).
        sharedPreferencesModule(SharedPreferencesModule(this)).
        build()
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this)
        }

    }




}