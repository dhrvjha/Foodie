package io.github.foodie.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import io.github.foodie.database.Foods
import io.github.foodie.util.SwrToast
import io.github.foodie.util.Urls
import org.json.JSONArray

class RestaurantDetailsAPI(private val context: Context, private val id: String) {
    val list: ArrayList<Foods> = ArrayList<Foods>()
    fun parseJson(jsonObject: JSONArray?) {
        jsonObject?.let {
            for (i in 0 until it.length()) {
                val data = it.getJSONObject(i)
                list.add(
                    Foods(
                        data.getString("name"),
                        data.getString("cost_for_one")
                    )
                )
            }
        }
    }

    fun getRequest(onSuccess: () -> Unit): JsonObjectRequest {
        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, Urls.RESTAURANT_DETAILS_URL + id, null, {
                try {
                    val jsonObject = it.getJSONObject("data")
                    if (jsonObject.getBoolean("success")) {
                        Log.d("APIException", jsonObject.toString(4))
                        parseJson(jsonObject.getJSONArray("data"))
                        onSuccess()
                    } else {
                        Toast.makeText(
                            context,
                            jsonObject.getString("errorMessage"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    SwrToast.show(context)
                }
            }, {
                SwrToast.show(context)
            }) {
                override fun getHeaders(): MutableMap<String, String> = CustomHeaders.headers()
            }
        jsonObjectRequest.tag = "restaurant-details"
        return jsonObjectRequest
    }
}