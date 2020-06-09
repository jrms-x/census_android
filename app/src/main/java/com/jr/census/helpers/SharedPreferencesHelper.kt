package com.jr.census.helpers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper constructor(val context : Context) {
    var sharedPreferences : SharedPreferences = context.getSharedPreferences("aqua_census",
        Context.MODE_PRIVATE)


    fun setToken(token : String?){
        writeStringPreference("token", token)
    }

    fun getToken() : String?{
        return getStringPreference("token")
    }

    fun writeStringPreference(key : String, value : String?) {
        with(sharedPreferences.edit()){
            putString(key, value)
            commit()
        }
    }

    fun writeIntPreference(key : String, value : Int) {
        with(sharedPreferences.edit()){
            putInt(key, value)
            commit()
        }
    }

    fun getStringPreference(key : String) : String?{
        return sharedPreferences.getString(key, null)
    }

    fun getIntPreference(key : String) : Int?{
        return sharedPreferences.getInt(key, 0)

    }

    fun getBlock() : Int? {
        return getIntPreference("block")
    }

    fun setBlock(block : Int){
        return writeIntPreference("block", block)
    }


}

