package com.jr.census.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.databinding.ActivityLoginBinding
import com.jr.census.helpers.SharedPreferencesHelper
import com.jr.census.helpers.isServerConnectionException
import com.jr.census.models.LoginResponse
import com.jr.census.viewmodel.UserViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var activityLoginBinding: ActivityLoginBinding?=null
    private lateinit var userViewModel: UserViewModel

    private var callbackLogin = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            Log.e("login", "Error on ws", t)
            userViewModel.loginUser.setLoading(false)
            val message =
                if(t.isServerConnectionException()){
                    R.string.error_connection
                }else{
                    R.string.login_failed
                }
            Toast.makeText(this@LoginActivity, message,
                Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(
            call: Call<LoginResponse>,
            response: Response<LoginResponse>
        ) {
            userViewModel.loginUser.setLoading(false)
            val loginResponse = response.body()
            if(loginResponse?.token != null){
                sharedPreferencesHelper.setToken(loginResponse.token)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("getCatalogs", true)
                this@LoginActivity.startActivity(intent)
                finish()

            }else{
                if(response.code() != 200){
                    Toast.makeText(this@LoginActivity,
                        R.string.user_or_password_invalid,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as CensusApplication).appComponent.inject(this)
        activityLoginBinding= DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )
        val view = activityLoginBinding!!.root
        userViewModel= ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        val version = applicationContext.packageManager?.getPackageInfo(packageName, 0)?.versionName
        view.versionTextView.text = getString(R.string.version, version)

        userViewModel.callbackLogin = callbackLogin

        activityLoginBinding?.viewModel = userViewModel

        userViewModel.callRegisterActivity = {
            goToRegister()
        }
    }

    private fun goToRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        finish()
    }
}