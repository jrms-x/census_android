package com.jr.census.models

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("token")
    var token : String? = null

}