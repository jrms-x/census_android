package com.jr.census.models

import com.google.gson.annotations.SerializedName

class Login (){

    constructor(username : String?, password : String?) : this(){
        this.username = username
        this.password = password
    }
    @SerializedName("user")
    var username : String? = null
    @SerializedName("password")
    var password : String? = null
}