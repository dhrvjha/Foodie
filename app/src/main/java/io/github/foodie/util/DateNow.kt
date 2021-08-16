package io.github.foodie.util

import java.text.SimpleDateFormat
import java.util.*

class DateNow {
    companion object {
        fun now(): String = SimpleDateFormat("dd/MM/yyyy").format(Date())
    }
}