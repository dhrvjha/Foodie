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

class ForgotPassword(
    private val context: Context,
    private val mobileNumber: String,
    private val email: String
) {
    private val jsonParams = JSONObject()
    private lateinit var jsonObjectRequest: JsonObjectRequest

    init {
        jsonParams.put("mobile_number", mobileNumber)
        jsonParams.put("email", email)
    }

    fun getRequest(sharedPreferences: SharedPreferences, onSuccess: () -> Unit): JsonObjectRequest {
        jsonObjectRequest =
            object : JsonObjectRequest(Method.POST, Urls.FORGOT_PASSWORD_URL, jsonParams, {
                try {
                    val jsonObject = it.getJSONObject("data")
                    if (jsonObject.getBoolean("success")) {
                        SharedPreferencesManager(sharedPreferences).saveCredentials(
                            jsonObject.getJSONObject(
                                "data"
                            )
                        )
                        onSuccess()
                    } else {
                        Toast.makeText(
                            context,
                            R.string.somethingWentWrong.toString(),
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
        jsonObjectRequest.tag = "forgot_password"
        return jsonObjectRequest
    }
}