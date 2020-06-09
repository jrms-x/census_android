package com.jr.census.models

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("username")
    var username : String? = null
    @SerializedName("UserID")
    var userID : Int = 0
}