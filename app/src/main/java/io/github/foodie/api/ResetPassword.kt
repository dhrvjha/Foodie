package io.github.foodie.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import io.github.foodie.util.SwrToast
import io.github.foodie.util.Urls
import org.json.JSONObject

class ResetPassword(
    private val context: Context,
    private val mobileNumber: String,
    private val otp: String,
    private val password: String
) {
    private val jsonParams = JSONObject()

    init {
        jsonParams.put("mobile_number", mobileNumber)
        jsonParams.put("password", password)
        jsonParams.put("otp", otp)
    }

    fun getRequest(sharedPreferences: SharedPreferences, onSuccess: () -> Unit): JsonObjectRequest {
        lateinit var jsonObjectRequest: JsonObjectRequest
        jsonObjectRequest = object :
            JsonObjectRequest(Request.Method.POST, Urls.RESET_PASSWORD_URL, jsonParams, {
                try {
                    val jsonObject = it.getJSONObject("data")
                    if (jsonObject.getBoolean("success")) {
                        onSuccess()
                    }
                    Toast.makeText(
                        context,
                        jsonObject.getString("successMessage"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Log.d("APIException", e.stackTraceToString())
                    SwrToast.show(context)
                }
            }, {
                SwrToast.show(context)
            }) {
            override fun getHeaders(): MutableMap<String, String> = CustomHeaders.headers()
        }
        jsonObjectRequest.tag = "reset_password"
        return jsonObjectRequest
    }
}