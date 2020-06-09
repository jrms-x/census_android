package com.jr.census.viewmodel

import android.app.Activity
import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jr.census.di.modules.PropertiesRepository
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.di.modules.BlocksRepository
import com.jr.census.fragments.ShowDialogBlock
import com.jr.census.helpers.OnResultFromWebService
import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.helpers.isServerConnectionException
import com.jr.census.models.Block
import com.jr.census.models.BlockPropertiesCount
import com.jr.census.models.Property
import com.jr.census.viewmodel.models.ListModel
import com.jr.census.viewmodel.models.SelectBlockModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel constructor(
    application: Application,
    val blocksRepository: BlocksRepository,
    val propertiesRepository: PropertiesRepository,
    val sharedPreferencesHelper: SharedPreferencesHelper
) :
    AndroidViewModel(application) {


    lateinit var listenerRefreshProperties: () -> Unit

    var showDialogBlock: ShowDialogBlock? = null


    val propertiesListModel: ListModel by lazy {
        ListModel()
    }

    val blocksListModel: ListModel by lazy {
        ListModel()
    }

    val propertiesLiveData: MutableLiveData<List<Property>?> by lazy {
        MutableLiveData<List<Property>?>()
    }

    var propertiesList: List<Property> = listOf()


    val blocksLiveData: LiveData<List<BlockPropertiesCount>> = blocksRepository.getBlocks()


    private val onResultProperty: OnResultFromWebService<List<Property>> =
        object : OnResultFromWebService<List<Property>> {
            override fun onSuccess(result: List<Property>?) {
                propertiesListModel.setLoading(false)
                if (result == null) {
                    viewModelScope.launch {
                        getPropertiesFromDb()
                    }
                } else {
                    propertiesLiveData.value = result
                    propertiesList = result
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            val block = Block()
                            block.id = selectBlockModel.getBlockNumber()!!
                            blocksRepository.insertBlock(block)
                            propertiesRepository.updateProperties(
                                result,
                                block.id
                            )

                            Log.d("database", "Saved properties")
                        }
                    }
                    val count = result.count()
                    if (count <= 0) {
                        setMessage(R.string.empty_properties)
                    } else {
                        setMessage()
                    }
                }
            }

            override fun onFailed(t: Throwable?) {
                propertiesListModel.setLoading(false)
                val message = if (t?.isServerConnectionException() == true) {
                    R.string.properties_no_connection
                } else {
                    R.string.properties_fail_response
                }
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                viewModelScope.launch {
                    getPropertiesFromDb()
                }
            }
        }

    private suspend fun getPropertiesFromDb() {

        propertiesList =
            propertiesRepository.getPropertiesFromDatabase(selectBlockModel.getBlockNumber()!!)
        propertiesLiveData.value = propertiesList
        if (propertiesList.count() <= 0) {
            setMessage(R.string.empty_properties)
        } else {
            setMessage()
        }
    }

    private var _blockListener: ((Boolean) -> Unit)? = null
    var blockListener: ((Boolean) -> Unit)?
        get() {
            return _blockListener
        }
        set(value) {
            _blockListener = value
            selectBlockModel.blockListener = blockListener
        }


    fun showDialog() {
        showDialogBlock?.showDialog()
    }

    fun saveBlockAndLoadProperties(activity: Activity) {
        sharedPreferencesHelper.setBlock(selectBlockModel.getBlockNumber()!!)
        getProperties(activity)

    }

    fun getProperties(activity: Activity) {
        propertiesListModel.setLoading(true)
        val blockNumber = selectBlockModel.getBlockNumber()
        if (blockNumber != null && blockNumber > 0) {
            val propertiesCallback = ResponseServiceCallback(onResultProperty, activity)
            propertiesRepository.getProperties(
                selectBlockModel.getBlockNumber()!!,
                propertiesCallback
            )

        } else {
            propertiesListModel.setLoading(false)
        }
    }


    var selectBlockModel: SelectBlockModel = SelectBlockModel.apply {
        val block = sharedPreferencesHelper.getBlock()
        if (block == null || block <= 0) {
            setBlock("")
        } else {
            setBlock(block.toString())
        }

    }

    fun removeToken() {
        sharedPreferencesHelper.setToken(null)
    }

    fun setMessage(message: Int? = null) {
        if (message != null) {
            propertiesListModel.setMessage(getApplication<CensusApplication>().getString(message))
        } else {
            propertiesListModel.setMessage(null)
        }
    }

    fun searchProperties(
        name: String?,
        serialNumber: String?,
        street: String?,
        number: String?,
        interiorNumber: String?
    ) {
        propertiesListModel.setMessage(null)
        viewModelScope.launch {
            propertiesLiveData.value = withContext(Dispatchers.Default) {
                val propertiesList = this@MainViewModel.propertiesList.filter { p ->
                    (TextUtils.isEmpty(name) || p.name?.contains(name!!, true) ?: true) &&
                            (TextUtils.isEmpty(serialNumber) || p.serialNumber?.contains(
                                serialNumber!!,
                                true
                            ) ?: true)
                            && (TextUtils.isEmpty(street) || p.street?.contains(
                        street!!,
                        true
                    ) ?: true)
                            && (TextUtils.isEmpty(number) || p.number?.contains(
                        number!!,
                        true
                    ) ?: true)
                            && (TextUtils.isEmpty(interiorNumber) || p.interior?.contains(
                        interiorNumber!!,
                        true
                    ) ?: true)
                }
                if (propertiesList.isEmpty()) {
                    this@MainViewModel.propertiesListModel.setMessage(
                        this@MainViewModel.getApplication<CensusApplication>()
                            .getString(R.string.properties_not_found)
                    )
                }
                propertiesList
            }
        }

    }


}
