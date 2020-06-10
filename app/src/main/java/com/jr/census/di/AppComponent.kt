package com.jr.census.di

import com.jr.census.activities.LaunchActivity
import com.jr.census.activities.LoginActivity
import com.jr.census.activities.MainActivity
import com.jr.census.activities.RegisterActivity
import com.jr.census.di.modules.*
import com.jr.census.fragments.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BlocksRepository::class, CatalogsRepository::class, LoginRepository::class,
    RegisterRepository::class, PropertiesRepository::class, ApplicationModule::class,
    DatabaseModule::class, ApiModule::class, SharedPreferencesModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(propertiesFragment: PropertiesFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(launchActivity: LaunchActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(propertyDetailFragment: PropertyDetailFragment)
    fun inject(propertyLandFragment: PropertyLandFragment)
    fun inject(propertyOutletFragment: PropertyOutletFragment)
    fun inject(propertyMeterFragment: PropertyMeterFragment)
    fun inject(propertyHydraulicsFragment: PropertyHydraulicsFragment)
    fun inject(propertySurveyFragment: PropertySurveyFragment)
    fun inject(propertyAttachmentFragment: PropertyAttachmentFragment)
    fun inject(propertyPicturesFragment: PropertyPicturesFragment)
    fun inject(propertyInformationFragment: PropertyInformationFragment)
}