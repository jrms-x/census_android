package com.jr.census

import android.app.Application
import com.jr.census.di.AppComponent
import com.jr.census.di.DaggerAppComponent
import com.jr.census.di.modules.ApplicationModule
import com.jr.census.di.modules.DatabaseModule
import com.jr.census.di.modules.SharedPreferencesModule

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
            com.facebook.soloader.SoLoader.init(this, false)
            if(com.facebook.flipper.android.utils.FlipperUtils.shouldEnableFlipper(this)){
                val client = com.facebook.flipper.android.AndroidFlipperClient.getInstance(this)
                client.addPlugin(com.facebook.flipper.plugins.inspector.
                InspectorFlipperPlugin(this, com.facebook.flipper.plugins.inspector.DescriptorMapping.withDefaults()))
                client.addPlugin(com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin(this))
                client.start()
            }
        }

    }




}