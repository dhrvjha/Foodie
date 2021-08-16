package io.github.foodie.util

import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import io.github.foodie.R
import io.github.foodie.database.Credentials
import org.json.JSONObject

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) {
    /**
     * @return true if logged in else false
     */
    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    /**
     * saves credentials in sharedPreferences credentials file
     * @param dataObject JSONObject of key 'data'
     */
    fun saveCredentials(dataObject: JSONObject){
        val edit = sharedPreferences.edit()
        edit.putBoolean("isLoggedIn", true)
        edit.putString("user_id", dataObject.getString("user_id"))
        edit.putString("name", dataObject.getString("name"))
        edit.putString("email", dataObject.getString("email"))
        edit.putString("mobile_number", dataObject.getString("mobile_number"))
        edit.putString("address", dataObject.getString("address"))
        edit.apply()
    }

    /**
     * @return Credentials Object with user information
     */
    fun get(): Credentials{
        return Credentials(
            sharedPreferences.getString("user_id", "")?:"null",
            sharedPreferences.getString("name", "")?:"",
            sharedPreferences.getString("email", "")?:"null",
            sharedPreferences.getString("mobile_number","")?:"null",
            sharedPreferences.getString("address","")?:"null"
        )
    }
    fun getUserName():String = sharedPreferences.getString("name", null)?:""
    fun getMobileNumber():String = sharedPreferences.getString("mobile_number",null)?:""
    fun getEmail():String = sharedPreferences.getString("email",null)?:""
    fun getAddress():String = sharedPreferences.getString("address",null)?:""

    fun getOrderId(): Int{
        val id:Int = sharedPreferences.getInt("order_id",0)
        sharedPreferences.edit().putInt("order_id", id+1).apply()
        return id
    }
}