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
 *  @param context Context of the activity
 *  @param mobileNumber mobile number from user input
 *  @param password password from user input
 */
class Login(
    private val context: Context,
    private val mobileNumber: String,
    private val password: String
) {

    private val jsonParams = JSONObject()
    private lateinit var jsonObjectRequest: JsonObjectRequest

    init {
        jsonParams.put("mobile_number", mobileNumber)
        jsonParams.put("password", password)
    }

    /**
     * @param sharedPreferences SharedPreferences to credentials file to save credentials
     * @param onSuccess function to execute on successful login
     * @return Request<T> returns request
     */
    fun getRequest(sharedPreferences: SharedPreferences, onSuccess: () -> Unit): JsonObjectRequest {
        jsonObjectRequest =
            object : JsonObjectRequest(Method.POST, Urls.LOGIN_URL, jsonParams, {
                try {
                    val jsonObject = it.getJSONObject("data")
                    if (jsonObject.getBoolean("success")) {
                        val dataObject = jsonObject.getJSONObject("data")
                        SharedPreferencesManager(sharedPreferences).saveCredentials(
                            dataObject
                        )
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
        jsonObjectRequest.tag = "login"
        return jsonObjectRequest
    }
}