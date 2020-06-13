package com.jr.census.helpers

interface OnResultFromWebService <T>{
    fun onSuccess(result : T?, statusCode : Int)
    fun onFailed(t : Throwable?)
}