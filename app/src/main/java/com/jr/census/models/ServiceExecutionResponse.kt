package com.jr.census.models

import com.google.gson.annotations.SerializedName

class ServiceExecutionResponse<T>
{
    @SerializedName("message")
    var message : String? = null

    @SerializedName("data")
    var data : T? = null
}