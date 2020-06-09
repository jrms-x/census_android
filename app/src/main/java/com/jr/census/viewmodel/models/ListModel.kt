package com.jr.census.viewmodel.models

import android.text.TextUtils
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR

class ListModel() : BaseObservable() {
    private var loading: Boolean = false
    private var message : String? = null

    @Bindable
    fun getMessageVisibility() : Int{
        return if(TextUtils.isEmpty(message)){
            View.GONE
        }else{
            View.VISIBLE
        }
    }


    @Bindable
    fun isLoading() : Boolean{
        return loading
    }

    @Bindable
    fun setLoading(value : Boolean){
        if(loading != value){
            loading = value
            notifyPropertyChanged(BR.loading)
        }
    }

    @Bindable
    fun getMessage() : String?{
        return message
    }

    @Bindable
    fun setMessage(value: String?){
        if(message != value){
            message =  value
            notifyPropertyChanged(BR.message)
            notifyPropertyChanged(BR.messageVisibility)
        }
    }

}