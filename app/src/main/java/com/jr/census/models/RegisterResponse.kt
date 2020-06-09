package com.jr.census.models

import com.google.gson.annotations.SerializedName

class RegisterResponse {
    @SerializedName("token")
    var token : String? = null
    @SerializedName("user")
    var user : User? = null
    override fun toString(): String {
        return "RegisterResponse(token=$token, user=$user)"
    }
}