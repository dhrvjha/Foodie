package io.github.foodie.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity
import io.github.foodie.R

class ConnectionAlert(private val context: Context) {
//    private val context = activity as Context
    private lateinit var alertBox: AlertDialog.Builder
    init {
        alertBox = AlertDialog.Builder(context)
        alertBox.setTitle(R.string.error)
        alertBox.setMessage(R.string.internetNotFound)
    }

    fun start(positiveButton: ()->Unit, negativeButton: ()->Unit): Boolean{
        alertBox.setPositiveButton(R.string.openSettings){_,_ ->
            positiveButton()
        }
        alertBox.setNegativeButton(R.string.exit){_,_ ->
            negativeButton()
        }
        if (!ConnectionManager().checkConnectivity(context)) {
            alertBox.create().show()
            return false
        }
        return true
    }

}