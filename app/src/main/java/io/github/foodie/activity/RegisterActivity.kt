package io.github.foodie.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.JsonObjectRequest
import io.github.foodie.R
import io.github.foodie.api.MySingleton
import io.github.foodie.api.Register
import io.github.foodie.util.ConnectionAlert

class RegisterActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var mobileNumber: EditText
    private lateinit var address: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var register: Button
    private lateinit var jsonObjectRequest: JsonObjectRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById<EditText>(R.id.edtxtRegisterName)
        email = findViewById(R.id.edtxtRegisterEmail)
        mobileNumber = findViewById(R.id.edtxtRegisterMobileNumber)
        address = findViewById(R.id.edtxtRegisterAddress)
        password = findViewById(R.id.edtxtRegisterPassword)
        confirmPassword = findViewById(R.id.edtxtRegisterConfirmPassword)
        register = findViewById(R.id.btnRegisterSignInButton)

        register.setOnClickListener {
            if (password.text.toString() == confirmPassword.text.toString()) {
                if (ConnectionAlert(this@RegisterActivity).start({
                        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                        finish()
                    }, {
                        ActivityCompat.finishAffinity(this@RegisterActivity)
                    })) {
                    jsonObjectRequest = Register(
                        this@RegisterActivity,
                        name.text.toString(),
                        email.text.toString(),
                        mobileNumber.text.toString(),
                        address.text.toString(),
                        password.text.toString()
                    ).getRequest(getSharedPreferences(getString(R.string.sharedLoginFile), MODE_PRIVATE)){
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                    MySingleton.getInstance(this@RegisterActivity).addToRequestQueue(jsonObjectRequest)
                }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    getString(R.string.passwordsDontMatch),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStop() {
        MySingleton.getInstance(this@RegisterActivity).cancelAll("register")
        super.onStop()
    }
}