package com.jr.census.models

import com.google.gson.annotations.SerializedName

class InformationResponse {
    @SerializedName("user")
    var user : User? = null
    @SerializedName("iat")
    var issuedTime : Long = 0
    @SerializedName("exp")
    var expirationTime : Long = 0
}