package com.jr.census.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jr.census.di.modules.RegisterRepository
import com.jr.census.models.RegisterResponse
import com.jr.census.viewmodel.models.RegisterUser
import retrofit2.Callback

class RegisterViewModel(application: Application, private val
registerRepository: RegisterRepository
) : AndroidViewModel(application) {

    lateinit var callbackRegister: Callback<RegisterResponse>
    var callLoginActivity: (() -> Unit)? = null

    val registerUser: RegisterUser by lazy {
        RegisterUser()
    }

    fun onLoginClick() {
        callLoginActivity?.invoke()
    }

    fun onRegisterClick() {
        registerUser.setLoading(true)

    }
}