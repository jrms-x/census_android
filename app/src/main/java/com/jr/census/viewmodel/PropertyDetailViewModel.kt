package com.jr.census.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jr.census.di.modules.CatalogsRepository
import com.jr.census.di.modules.PicturesRepository
import com.jr.census.models.*
import com.jr.census.view.callback.ImageListListener
import com.jr.census.viewmodel.models.CensusData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class PropertyDetailViewModel(application: Application,
                              private val catalogsRepository : CatalogsRepository,
                              private val picturesRepository: PicturesRepository) : AndroidViewModel(application) {

    lateinit var property : Property
    var callCameraFunction : (() -> Unit)? = null
    var callActionMode : (() -> Unit)? = null
    var callIsSelectMode : (() -> Boolean)? = null
    var finishActionMode : (() -> Unit)? = null
    var callEditPicture : ((Picture) -> Unit)? = null
    var file : File? = null
    lateinit var title : String

    //todo initialize census from server
    val censusData  : CensusData by lazy {
        CensusData()
    }

    val picturesLiveData : LiveData<List<Picture>> by lazy {
        picturesRepository.getPictures(property.id)
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

    val pictureListener: ImageListListener = object : ImageListListener{
        override fun onSelectEdit(picture: Picture) {
            callEditPicture?.invoke(picture)
        }

        override fun onSelectImage(picture: Picture) {

        }

        override fun startSelection() {
            callActionMode?.invoke()
        }

        override fun isSelectMode(): Boolean {
            return callIsSelectMode?.invoke() ?: false
        }

        override fun finishSelection() {
            finishActionMode?.invoke()
        }

    }



    /*init{ //only test

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
    }*/

    fun takePhoto(){
        callCameraFunction?.invoke()
    }

    fun savePicture(location: String) {
        val order = (picturesRepository.getLastPicture(property.id)?.order ?: 0) + 1


        val picture = Picture( location, order, property.id)
        picturesRepository.insertPicture(picture)
    }

    fun deletePictures(selectedPictures: List<Picture>?) {
        if(selectedPictures != null){
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    //todo, delete from server
                    picturesRepository.deletePictures(selectedPictures)
                    launch {
                        withContext(Dispatchers.IO){
                            for(p in selectedPictures){
                                val image = File(p.location)
                                image.delete()
                            }
                        }
                    }
                }
            }

            finishActionMode?.invoke()
        }

    }

    fun updatePicture(picture: Picture) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                picturesRepository.updatePicture(picture)
            }

        }
    }
}