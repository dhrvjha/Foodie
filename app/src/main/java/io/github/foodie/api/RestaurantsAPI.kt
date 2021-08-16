package io.github.foodie.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import io.github.foodie.database.Restaurants
import io.github.foodie.util.SwrToast
import io.github.foodie.util.Urls
import org.json.JSONArray

class RestaurantsAPI(private val context: Context) {
    var restaurants: ArrayList<Restaurants> = ArrayList<Restaurants>()

    fun parseJson(jsonObject: JSONArray?) {
        jsonObject?.let {
            for (i in 0 until it.length()) {
                val data = it.getJSONObject(i)
                restaurants.add(
                    Restaurants(
                        data.getString("id").toString().toInt(),
                        data.getString("name"),
                        data.getString("rating"),
                        data.getString("cost_for_one"),
                        data.getString("image_url")
                    )
                )
            }
        }
    }

    fun getRequest(onSuccess: () -> Unit): JsonObjectRequest {
        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET, Urls.RESTAURANTS_URL, null, {
            try {
                val jsonObject = it.getJSONObject("data")
                if (jsonObject.getBoolean("success")) {
                    parseJson(jsonObject.getJSONArray("data"))
                    onSuccess()
                } else {
                    SwrToast.show(context)
                }
            } catch (e: Exception) {
                Log.d("APIException",e.stackTraceToString())
                SwrToast.show(context)
            }
        }, {
            SwrToast.show(context)
        }) {
            override fun getHeaders(): MutableMap<String, String> = CustomHeaders.headers()
        }
        jsonObjectRequest.tag = "list-restaurants"
        return jsonObjectRequest
    }
}