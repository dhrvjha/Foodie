package io.github.foodie.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.JsonObjectRequest
import io.github.foodie.R
import io.github.foodie.api.Login
import io.github.foodie.api.MySingleton
import io.github.foodie.util.ConnectionAlert
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mobileNumber: EditText
    private lateinit var password: EditText
    private lateinit var forgotPassword: TextView
    private lateinit var signUp: TextView
    private lateinit var loginButton: Button
    private lateinit var jsonObjectRequest: JsonObjectRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mobileNumber = findViewById(R.id.edtxtLoginMobileNumber)
        password = findViewById(R.id.edtxtLoginPassword)
        forgotPassword = findViewById(R.id.txtLoginForgotPasswordButton)
        loginButton = findViewById(R.id.btnLoginSignInButton)
        sharedPreferences = getSharedPreferences(getString(R.string.sharedLoginFile), MODE_PRIVATE)
        signUp = findViewById(R.id.txtLoginSignUpButton)
        signUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        forgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
        loginButton.setOnClickListener {
            val jsonParams = JSONObject()

            if (ConnectionAlert(this@LoginActivity).start({
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                    finish()
                }, {
                    ActivityCompat.finishAffinity(this@LoginActivity)
                })) {
                jsonObjectRequest = Login(
                    this@LoginActivity,
                    mobileNumber.text.toString(),
                    password.text.toString()
                ).getRequest(sharedPreferences) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
            }
        }
    }

    override fun onStop() {
        MySingleton.getInstance(this).cancelAll("login")
        super.onStop()
    }
}