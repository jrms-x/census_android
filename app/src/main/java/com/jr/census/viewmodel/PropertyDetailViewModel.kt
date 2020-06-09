package com.jr.census.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jr.census.models.Picture
import com.jr.census.models.Property
import java.io.File

class PropertyDetailViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var property : Property
    var callCameraFunction : (() -> Unit)? = null
    var file : File? = null
    lateinit var title : String

    val picturesLiveData : MutableLiveData<List<Picture>> by lazy {
        MutableLiveData<List<Picture>>()
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