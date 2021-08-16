package io.github.foodie.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import io.github.foodie.R
import io.github.foodie.util.SharedPreferencesManager
import io.github.foodie.util.Urls
import org.json.JSONObject


/**
 * @param context Context of the activity
 * @param name name of users
 * @param email email address of user
 * @param mobileNumber mobile number of user
 * @param address address of user
 * @param password password of user
 */
class Register(
    private val context: Context,
    private val name: String,
    private val email: String,
    private val mobileNumber: String,
    private val address: String,
    private val password: String
) {
    /**
     * @param sharedPreferences SharedPreferences of credentials file
     * @param onSuccess function to execute on successful registration
     * @return JsonObjectRequest containing request params
     */
    private lateinit var jsonObjectRequest: JsonObjectRequest
    private val jsonParams = JSONObject()

    init {
        jsonParams.put("name", name)
        jsonParams.put("mobile_number", mobileNumber)
        jsonParams.put("password", password)
        jsonParams.put("email", email)
        jsonParams.put("address", address)
    }

    fun getRequest(sharedPreferences: SharedPreferences, onSuccess: () -> Unit): JsonObjectRequest {
        jsonObjectRequest =
            object : JsonObjectRequest(Method.POST, Urls.REGISTER_URL, jsonParams, {
                try {
                    val jsonObject = it.getJSONObject("data")
                    if (jsonObject.getBoolean("success")) {
                        SharedPreferencesManager(
                            sharedPreferences
                        ).saveCredentials(jsonObject.getJSONObject("data"))
                        onSuccess()
                    } else {
                        Toast.makeText(
                            context,
                            jsonObject.getString("errorMessage"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.d("APIException", e.stackTraceToString())
                    Toast.makeText(
                        context,
                        R.string.somethingWentWrong.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, {
                Toast.makeText(
                    context,
                    R.string.somethingWentWrong.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                override fun getHeaders(): MutableMap<String, String> = CustomHeaders.headers()
            }
        jsonObjectRequest.tag = "register"
        return jsonObjectRequest
    }
}