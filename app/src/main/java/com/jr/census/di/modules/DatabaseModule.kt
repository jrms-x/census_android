package com.jr.census.di.modules

import android.app.Application
import com.jr.census.service.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule constructor(val application : Application) {

    @Singleton
    @Provides
    fun getAppDatabase() : AppDatabase = AppDatabase.getInstance(application)

}