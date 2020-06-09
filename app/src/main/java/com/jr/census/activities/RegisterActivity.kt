package com.jr.census.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.databinding.ActivityRegisterBinding
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.helpers.isServerConnectionException
import com.jr.census.models.RegisterResponse
import com.jr.census.viewmodel.RegisterViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var activityRegisterBinding: ActivityRegisterBinding?=null
    private lateinit var userViewModel: RegisterViewModel

    private var callbackRegister = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            Log.e("register", "Error on ws", t)
            userViewModel.registerUser.setLoading(false)
            val message =
                if(t.isServerConnectionException()){
                    R.string.error_connection
                }else{
                    R.string.login_failed
                }
            Toast.makeText(this@RegisterActivity, message,
                Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(
            call: Call<RegisterResponse>,
            response: Response<RegisterResponse>
        ) {
            userViewModel.registerUser.setLoading(false)
            val registerResponse = response.body()
            TODO("Register user from web service")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as CensusApplication).appComponent.inject(this)
        activityRegisterBinding= DataBindingUtil.setContentView(this,
            R.layout.activity_register
        )
        val view = activityRegisterBinding!!.root
        userViewModel= ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)


        userViewModel.callbackRegister = callbackRegister

        activityRegisterBinding?.viewModel = userViewModel

        userViewModel.callLoginActivity = {
            goToLogin()
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        finish()
    }
}