package com.jr.census.helpers

interface OnResultFromWebService <T>{
    fun onSuccess(result : T?)
    fun onFailed(t : Throwable?)
}