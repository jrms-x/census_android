package com.jr.census.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jr.census.di.modules.LoginRepository
import com.jr.census.models.Login
import com.jr.census.models.LoginResponse
import com.jr.census.viewmodel.models.LoginUser
import retrofit2.Callback


class UserViewModel(application : Application, private val
    loginRepository: LoginRepository): AndroidViewModel(application) {

    lateinit var callbackLogin : Callback<LoginResponse>
    var callRegisterActivity : (() -> Unit)? = null

    val loginUser : LoginUser by lazy {
        LoginUser()
    }

    fun onLoginClick() {
        loginUser.setLoading(true)
        val login = Login(loginUser.getUser(), loginUser.getPassword())
        loginRepository.getLogin(login, callbackLogin)
    }

    fun onRegisterClick() {
        callRegisterActivity?.invoke()
    }


}