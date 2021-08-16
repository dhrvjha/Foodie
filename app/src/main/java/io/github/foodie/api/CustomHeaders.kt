package io.github.foodie.api

class CustomHeaders {
    companion object {
        fun headers(): MutableMap<String, String>{
            val header = HashMap<String, String>()
            header["Content-type"] = "application/json"
            header["token"] = "5ac85abecd8072"
            return header
        }
    }
}