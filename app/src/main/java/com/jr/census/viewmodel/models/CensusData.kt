package com.jr.census.viewmodel.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR

class CensusData : BaseObservable() {
    private var propertyTypePosition : Int = 0
    private var chargeTypePosition : Int = 0
    private var anomalyPosition : Int = 0
    private var outletTypePosition : Int = 0
    private var meterBrandPosition : Int = 0
    private var meterStatusPosition : Int = 0
    private var protectionTypePosition : Int = 0

    @Bindable
    fun getPropertyTypePosition() : Int{
        return propertyTypePosition
    }

    @Bindable
    fun getChargeTypePosition() : Int{
        return chargeTypePosition
    }

    @Bindable
    fun getAnomalyPosition() : Int{
        return anomalyPosition
    }

    @Bindable
    fun getOutletTypePosition() : Int{
        return outletTypePosition
    }

    @Bindable
    fun getMeterBrandPosition() : Int{
        return meterBrandPosition
    }

    @Bindable
    fun getMeterStatusPosition() : Int{
        return meterStatusPosition
    }

    @Bindable
    fun getProtectionTypePosition() : Int{
        return protectionTypePosition
    }

    @Bindable
    fun setAnomalyPosition(value : Int){
        if(value != anomalyPosition){
            anomalyPosition = value
            notifyPropertyChanged(BR.anomalyPosition)
        }
    }

    @Bindable
    fun setMeterBrandPosition(value : Int){
        if(value != meterBrandPosition){
            meterBrandPosition = value
            notifyPropertyChanged(BR.meterBrandPosition)
        }
    }

    @Bindable
    fun setMeterStatusPosition(value : Int){
        if(value != meterStatusPosition){
            meterStatusPosition = value
            notifyPropertyChanged(BR.meterStatusPosition)
        }
    }

    @Bindable
    fun setProtectionTypePosition(value : Int){
        if(value != protectionTypePosition){
            protectionTypePosition = value
            notifyPropertyChanged(BR.protectionTypePosition)
        }
    }

    @Bindable
    fun setPropertyTypePosition(value : Int){
        if(value != propertyTypePosition){
            propertyTypePosition = value
            notifyPropertyChanged(BR.propertyTypePosition)
        }
    }

    @Bindable
    fun setChargeTypePosition(value : Int){
        if(value != chargeTypePosition){
            chargeTypePosition = value
            notifyPropertyChanged(BR.chargeTypePosition)
        }
    }

    @Bindable
    fun setOutletTypePosition(value : Int){
        if(value != outletTypePosition){
            outletTypePosition = value
            notifyPropertyChanged(BR.outletTypePosition)
        }
    }

}