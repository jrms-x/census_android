package com.jr.census.helpers

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.jr.census.activities.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.*

class ResponseServiceCallback<T>(private val onResult: OnResultFromWebService<T>,
                                 private val activity: Activity?) : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e("response","service error", t)
        onResult.onFailed(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if(response.code() == 401){
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()

        }else{
            onResult.onSuccess(response.body(), response.code())

        }
    }
}

fun Throwable.isServerConnectionException() : Boolean{
    return this is BindException
            || this is ConnectException
            || this is HttpRetryException
            || this is MalformedURLException
            || this is NoRouteToHostException
            || this is PortUnreachableException
            || this is ProtocolException
            || this is SocketException
            || this is SocketTimeoutException
            || this is UnknownHostException
            || this is UnknownServiceException
            || this is URISyntaxException
}