package com.jr.census.viewmodel.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR

class PictureData() : BaseObservable() {
    private var title : String? = null
    private var subtitle : String? = null
    private var description : String? = null

    constructor(title : String?, subtitle : String?, description : String?) : this(){
        this.title = title
        this.subtitle = subtitle
        this.description = description
    }

    @Bindable
    fun getTitle() : String?{
        return title
    }

    @Bindable
    fun getSubtitle() : String?{
        return subtitle
    }

    @Bindable
    fun getDescription() : String?{
        return description
    }

    @Bindable
    fun setTitle(value : String?){
        if(title != value){
            title = value
            notifyPropertyChanged(BR.title)
        }
    }

    @Bindable
    fun setSubtitle(value : String?){
        if(subtitle != value){
            subtitle = value
            notifyPropertyChanged(BR.subtitle)
        }
    }

    @Bindable
    fun setDescription(value : String?){
        if(description != value){
            description = value
            notifyPropertyChanged(BR.description)
        }
    }
}