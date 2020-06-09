package com.jr.census.viewmodel.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR

class PropertySearchModel : BaseObservable() {
    private var name : String? = null
    private var serialNumber : String? = null
    private var street : String? = null
    private var number : String? = null
    private var interiorNumber : String? = null

    @Bindable
    fun getName() : String?{
        return name
    }

    @Bindable
    fun setName(value : String?){
        if(name != value){
            name = value
            notifyPropertyChanged(BR.name)
        }
    }

    @Bindable
    fun getSerialNumber() : String?{
        return serialNumber
    }

    @Bindable
    fun setSerialNumber(value : String?){
        if(serialNumber != value){
            serialNumber = value
            notifyPropertyChanged(BR.serialNumber)
        }
    }

    @Bindable
    fun getStreet() : String?{
        return street
    }

    @Bindable
    fun setStreet(value : String?){
        if(street != value){
            street = value
            notifyPropertyChanged(BR.street)
        }
    }

    @Bindable
    fun getNumber() : String?{
        return number
    }

    @Bindable
    fun setNumber(value : String?){
        if(number != value){
            number = value
            notifyPropertyChanged(BR.number)
        }
    }

    @Bindable
    fun getInteriorNumber() : String?{
        return interiorNumber
    }

    @Bindable
    fun setInteriorNumber(value : String?){
        if(interiorNumber != value){
            interiorNumber = value
            notifyPropertyChanged(BR.interiorNumber)
        }
    }

}