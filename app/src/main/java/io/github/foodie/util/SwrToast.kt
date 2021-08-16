package io.github.foodie.util

import android.content.Context
import android.widget.Toast
import io.github.foodie.R

class SwrToast() {
    companion object {
        fun show(context: Context) {
            Toast.makeText(context, R.string.somethingWentWrong.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}