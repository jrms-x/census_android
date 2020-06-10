package com.jr.census.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jr.census.di.modules.CatalogsRepository
import com.jr.census.models.*
import com.jr.census.viewmodel.models.CensusData
import java.io.File

class PropertyDetailViewModel(application: Application,
                              private val catalogsRepository : CatalogsRepository) : AndroidViewModel(application) {
    lateinit var property : Property
    var callCameraFunction : (() -> Unit)? = null
    var file : File? = null
    lateinit var title : String

    //todo initialize census from server
    var censusData  = CensusData()

    val picturesLiveData : MutableLiveData<List<Picture>> by lazy {
        MutableLiveData<List<Picture>>()
    }

    val chargeTypesLiveData : LiveData<List<ChargeType>> by lazy{
        catalogsRepository.getChargeTypes()
    }

    val meterBrandsLiveData : LiveData<List<MeterBrand>> by lazy{
        catalogsRepository.getMeterBrands()
    }

    val meterStatusLiveData : LiveData<List<MeterStatus>> by lazy {
        catalogsRepository.getMeterStatus()
    }

    val outletLiveData : LiveData<List<OutletType>> by lazy{
        catalogsRepository.getOutletTypes()
    }

    val propertyTypesLiveData : LiveData<List<PropertyType>> by lazy{
        catalogsRepository.getPropertyTypes()
    }

    val protectionTypesLiveData : LiveData<List<ProtectionType>> by lazy{
        catalogsRepository.getProtectionTypes()
    }

    val anomaliesLiveData : LiveData<List<Anomaly>> by lazy {
        catalogsRepository.getAnomalies()
    }



    init{ //only test

        picturesLiveData.value = listOf(
            Picture("Perro",
                "Imágen de perro",
                "https://wallpapercart.com/wp-content/uploads/2019/12/Animal-Dog-Dogs-Pet-Depth-Of-Field-HD-Wallpaper-Background-Image.jpg",
                "Este es una imágen de un perro"),
            Picture("Gato",
                "Imágen de gato",
                "https://newevolutiondesigns.com/images/freebies/cat-wallpaper-15.jpg",
                "Este es una imágen de un gato"),
            Picture("Camaleón",
                "Imágen de camaleón",
                "https://images7.alphacoders.com/503/503155.jpg",
                "Este es una imágen de un camaleón")

        )
    }

    fun takePhoto(){
        callCameraFunction?.invoke()
    }
}