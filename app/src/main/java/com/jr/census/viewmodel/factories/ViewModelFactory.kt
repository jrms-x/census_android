package com.jr.census.viewmodel.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jr.census.di.modules.*
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.viewmodel.MainViewModel
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.RegisterViewModel
import com.jr.census.viewmodel.UserViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")

class ViewModelFactory @Inject constructor  (private val propertiesRepository: PropertiesRepository,
                                             private val blocksRepository: BlocksRepository,
                                             private val loginRepository: LoginRepository,
                                             private val registerRepository: RegisterRepository,
                                             private val catalogsRepository: CatalogsRepository,
                                             private val picturesRepository: PicturesRepository,
                                             private val application: Application,
                                             private val sharedPreferencesHelper: SharedPreferencesHelper)

    : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(application,blocksRepository,
                    propertiesRepository, sharedPreferencesHelper) as T
            }
            modelClass.isAssignableFrom(PropertyDetailViewModel::class.java) -> {
                PropertyDetailViewModel(application, catalogsRepository, picturesRepository) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(application, loginRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(application, registerRepository) as T
            }
            else -> throw IllegalArgumentException("Invalid ViewModel of type " + modelClass)
        }
    }
}