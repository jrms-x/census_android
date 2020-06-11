package com.jr.census

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.jr.census.BuildConfig
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
        SoLoader.init(this, false)
        if(BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)){
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()
        }

    }




}