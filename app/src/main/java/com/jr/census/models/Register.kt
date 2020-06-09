package com.jr.census.models

import com.google.gson.annotations.SerializedName

class Register() {
    constructor(username : String?, password : String?) : this(){
        this.username = username
        this.password = password
    }
    @SerializedName("username")
    var username : String? = null
    @SerializedName("password")
    var password : String? = null
}