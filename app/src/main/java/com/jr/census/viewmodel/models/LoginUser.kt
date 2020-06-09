package com.jr.census.viewmodel.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR

class LoginUser : BaseObservable() {
    private var user : String? = null
    private var password : String? = null
    private var loading : Boolean = false

    @Bindable
    fun getUser() : String?{
        return user
    }

    @Bindable
    fun setUser(value : String) {
        if (value != user) {
            user = value
            notifyPropertyChanged(BR.user)
        }
    }

    @Bindable
    fun getPassword() : String?{
        return password
    }

    @Bindable
    fun setPassword(value : String?){
        if(value != password){
            password = value
            notifyPropertyChanged(BR.password)
        }
    }

    @Bindable
    fun getLoading() : Boolean{
        return loading
    }

    @Bindable
    fun setLoading(value : Boolean){
        if(loading != value){
            loading = value
            notifyPropertyChanged(BR.loading)
        }
    }

    override fun toString(): String {
        return "LoginUser(user=$user, password=$password)"
    }
}