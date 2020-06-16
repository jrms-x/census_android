package com.jr.census.viewmodel

import android.app.Activity
import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jr.census.R
import com.jr.census.di.modules.CatalogsRepository
import com.jr.census.di.modules.PicturesRepository
import com.jr.census.di.modules.PropertiesRepository
import com.jr.census.helpers.OnResultFromWebService
import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.models.*
import com.jr.census.view.callback.ImageListListener
import com.jr.census.viewmodel.models.CensusData
import com.jr.census.viewmodel.models.ERROR
import com.jr.census.viewmodel.models.SYNC
import com.jr.census.viewmodel.models.SYNCED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class PropertyDetailViewModel(
    application: Application,
    private val propertiesRepository: PropertiesRepository,
    private val catalogsRepository: CatalogsRepository,
    private val picturesRepository: PicturesRepository
) : AndroidViewModel(application) {


    lateinit var property: Property


    var callCameraFunction: (() -> Unit)? = null
    var callActionMode: (() -> Unit)? = null
    var callIsSelectMode: (() -> Boolean)? = null
    var finishActionMode: (() -> Unit)? = null
    var callEditPicture: ((Picture) -> Unit)? = null
    var callShowPicture: ((File) -> Unit)? = null

    var file: File? = null
    var startSync = true

    lateinit var title: String

    private val tableProperties = Hashtable<Int, Boolean?>()

    val censusData: CensusData by lazy {
        CensusData()
    }

    val picturesLiveData: LiveData<List<Picture>> by lazy {
        picturesRepository.getPictures(property.id)
    }

    val chargeTypesLiveData: LiveData<List<ChargeType>> by lazy {
        catalogsRepository.getChargeTypes()
    }

    val meterBrandsLiveData: LiveData<List<MeterBrand>> by lazy {
        catalogsRepository.getMeterBrands()
    }

    val meterStatusLiveData: LiveData<List<MeterStatus>> by lazy {
        catalogsRepository.getMeterStatus()
    }

    val outletLiveData: LiveData<List<OutletType>> by lazy {
        catalogsRepository.getOutletTypes()
    }

    val propertyTypesLiveData: LiveData<List<PropertyType>> by lazy {
        catalogsRepository.getPropertyTypes()
    }

    val protectionTypesLiveData: LiveData<List<ProtectionType>> by lazy {
        catalogsRepository.getProtectionTypes()
    }

    val anomaliesLiveData: LiveData<List<Anomaly>> by lazy {
        catalogsRepository.getAnomalies()
    }

    fun loadCensusData() {
        viewModelScope.launch {
            withContext(IO) {
                if (property.census == null) {
                    property.census =
                        propertiesRepository.getCensusData(
                            property.id,
                            Calendar.getInstance().get(Calendar.YEAR)
                        )
                }
            }
        }
    }


    val pictureListener: ImageListListener = object : ImageListListener {
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

        override fun canShowLoading(): Boolean {
            return property.census != null
        }

    }


    fun takePhoto() {
        callCameraFunction?.invoke()
    }

    suspend fun savePicture(location: String) = withContext(IO){
        val order = (picturesRepository.getLastPicture(property.id)?.order ?: 0) + 1
        val picture = Picture(location, order, property.id)
        picturesRepository.insertPicture(picture)
    }

    fun deletePictures(activity: Activity,selectedPictures: List<Picture>?) {
        if (selectedPictures != null) {
            MainScope().launch {
                withContext(IO) {
                    //todo, delete from server
                    for(p in selectedPictures){
                        picturesRepository.deletePicture(p, ResponseServiceCallback(object : OnResultFromWebService<ServiceExecutionResponse<Any?>?>{
                            override fun onSuccess(
                                result: ServiceExecutionResponse<Any?>?,
                                statusCode: Int
                            ) {
                                if(statusCode == 200){
                                    MainScope().launch {
                                        withContext(IO) {
                                            picturesRepository.deletePicture(p)
                                            val image = File(p.location)
                                            image.delete()

                                        }
                                    }

                                }else{
                                    Toast.makeText(activity, R.string.errorDeletingPicture, Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailed(t: Throwable?) {
                                Toast.makeText(activity, R.string.errorDeletingPictureServer, Toast.LENGTH_SHORT).show()
                            }

                        }, activity))
                    }


                }
            }

            finishActionMode?.invoke()
        }

    }

    fun updatePicture(picture: Picture) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                picturesRepository.updatePicture(picture)
            }

        }
    }

    fun saveCensusData(activity: Activity) {
        viewModelScope.launch {
            withContext(IO) {
                val anomalies = anomaliesLiveData.value
                val protectionTypes = protectionTypesLiveData.value
                val propertyTypes = propertyTypesLiveData.value
                val outletTypes = outletLiveData.value
                val metersStatus = meterStatusLiveData.value
                val meterBrands = meterBrandsLiveData.value
                val chargeType = chargeTypesLiveData.value

                property.census = PropertyCensusInformation()

                property.census!!.idAnomaly =
                    anomalies?.get(censusData.getAnomalyPosition())?.id ?: 0
                property.census!!.idTypesProtections =
                    protectionTypes?.get(censusData.getProtectionTypePosition())?.id ?: 0
                property.census!!.idTypesProperty =
                    propertyTypes?.get(censusData.getPropertyTypePosition())?.id ?: 0
                property.census!!.idTypesOutlet =
                    outletTypes?.get(censusData.getOutletTypePosition())?.id ?: 0
                property.census!!.idMeterStatus =
                    metersStatus?.get(censusData.getMeterStatusPosition())?.id ?: 0
                property.census!!.idMeterBrands =
                    meterBrands?.get(censusData.getMeterBrandPosition())?.id ?: 0
                property.census!!.idTypesCharge =
                    chargeType?.get(censusData.getChargeTypePosition())?.id ?: 0
                property.census!!.idProperty = property.id


                propertiesRepository.saveCensusData(
                    property.census!!, ResponseServiceCallback(
                        object : OnResultFromWebService<ServiceExecutionResponse<Any>> {
                            override fun onSuccess(
                                result: ServiceExecutionResponse<Any>?,
                                statusCode: Int
                            ) {
                                viewModelScope.launch {
                                    withContext(IO) {
                                        propertiesRepository.saveCensusDataIntoDatabase(
                                            property.census!!
                                        )
                                    }
                                    Toast.makeText(
                                        getApplication(),
                                        R.string.savedCensusData,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()

                                    withContext(IO) {
                                        synchronizePictures(activity)
                                    }
                                }
                            }

                            override fun onFailed(t: Throwable?) {
                                Toast.makeText(
                                    getApplication(),
                                    R.string.errorSavingCensusData,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        }, activity
                    )
                )
            }


        }
    }

    fun synchronizePictures(activity: Activity) {
        if(startSync){
            val table = this.tableProperties

            if (property.census != null) {
                picturesLiveData.value?.forEach { p ->
                    if(table[p.idLocal] != true){
                        MainScope().launch {
                            val pictureDb : Picture? =
                            withContext(IO){
                                picturesRepository.getPicture(p.idLocal)
                            }
                            if(pictureDb != null){
                                uploadPicture(pictureDb, activity)
                            }
                        }
                    }

                }
            }
        }



    }

    suspend fun uploadPicture(p: Picture, activity: Activity) {
        if (p.isImageAvailableToUpload() && TextUtils.isEmpty(p.idServer?.trim()) && tableProperties[p.idLocal] != true) {
            tableProperties[p.idLocal] = true

            picturesRepository.addPicture(
                p, ResponseServiceCallback(
                    object :
                        OnResultFromWebService<ServiceExecutionResponse<String?>?> {
                        override fun onSuccess(
                            result: ServiceExecutionResponse<String?>?,
                            statusCode: Int
                        ) {
                            MainScope().launch {
                                if (result?.data != null) {
                                    p.setSynchronized(SYNCED)
                                    p.idServer = result.data
                                    withContext(IO) {
                                        picturesRepository.updatePicture(p)
                                    }
                                } else {
                                    setError(p)
                                }
                            }
                            tableProperties[p.idLocal] = false
                        }

                        override fun onFailed(t: Throwable?) {

                            tableProperties[p.idLocal] = false
                            setError(p)
                            Log.e("Picture", "Error uploading", t)
                        }

                        fun setError(p : Picture){
                            startSync = false
                            p.setSynchronized(ERROR)
                            MainScope().launch {
                                withContext(IO) {
                                    picturesRepository.updatePicture(p)
                                }
                            }
                        }

                    }, activity
                )
            )

        }
    }

    fun setAnomalySelection(list: List<Anomaly>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setAnomalyPosition(list.indexOfFirst { a -> a.id == property.census?.idAnomaly ?: 0 })
        }
    }

    fun setProtectionTypeSelection(list: List<ProtectionType>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setProtectionTypePosition(list.indexOfFirst { a -> a.id == property.census?.idTypesProtections ?: 0 })
        }


    }

    fun setPropertyTypeSelection(list: List<PropertyType>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setPropertyTypePosition(list.indexOfFirst { a -> a.id == property.census?.idTypesProperty ?: 0 })
        }
    }

    fun setOutletTypeSelection(list: List<OutletType>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setOutletTypePosition(list.indexOfFirst { a -> a.id == property.census?.idTypesOutlet ?: 0 })
        }


    }

    fun setMeterStatusSelection(list: List<MeterStatus>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setMeterStatusPosition(list.indexOfFirst { a -> a.id == property.census?.idMeterStatus ?: 0 })
        }


    }

    fun setMeterBrandsSelection(list: List<MeterBrand>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setMeterBrandPosition(list.indexOfFirst { a -> a.id == property.census?.idMeterBrands ?: 0 })
        }

    }

    fun setChargeTypeSelection(list: List<ChargeType>?) {
        if (list?.isNotEmpty() == true) {
            censusData.setChargeTypePosition(list.indexOfFirst { a -> a.id == property.census?.idTypesCharge ?: 0 })
        }
    }

    fun setAllPicturesWithErrorToSync(){
        startSync = true
        MainScope().launch {
            withContext(IO){
                picturesRepository.setAllPicturesWithErrorToSync(property.id)
            }
        }
    }

    fun saveLocation(activity: Activity) {
        propertiesRepository.updateProperty(property, ResponseServiceCallback(object : OnResultFromWebService<ServiceExecutionResponse<Any?>?>{
            override fun onSuccess(result: ServiceExecutionResponse<Any?>?, statusCode: Int) {
                if(statusCode == 200){
                    viewModelScope.launch {
                        withContext(IO){
                            propertiesRepository.updatePropertyDatabase(property)
                        }
                        Toast.makeText(activity, R.string.savedLocation, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(activity, R.string.errorSavingLocation, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailed(t: Throwable?) {
                t?.printStackTrace()
                Toast.makeText(activity, R.string.errorSavingLocationServer, Toast.LENGTH_SHORT).show()
            }

        }, activity))
    }
}