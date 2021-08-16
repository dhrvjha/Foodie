package io.github.foodie.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectionManager {
    /**
     * @param context Context
     * @return true if connection avialable else false
     */
    fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }

}