package io.github.foodie.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import io.github.foodie.R
import io.github.foodie.api.ForgotPassword
import io.github.foodie.api.MySingleton
import io.github.foodie.util.ConnectionAlert

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var mobileNumber: EditText
    private lateinit var email: EditText
    private lateinit var nextButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mobileNumber = findViewById<EditText>(R.id.edtxtForgotPasswordMobileNumber)
        email = findViewById<EditText>(R.id.edtxtForgotPasswordEmail)
        nextButton = findViewById<Button>(R.id.btnForgotPasswordNextButton)

        nextButton.setOnClickListener {
            if (ConnectionAlert(this@ForgotPasswordActivity).start({
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                    finish()
                }, {
                    ActivityCompat.finishAffinity(this@ForgotPasswordActivity)
                })) {
                val jsonObjectRequest = ForgotPassword(
                    this@ForgotPasswordActivity,
                    mobileNumber.text.toString(),
                    email.text.toString()
                ).getRequest(getSharedPreferences(getString(R.string.sharedLoginFile), MODE_PRIVATE)){
                    val intent = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
                    intent.putExtra("mobileNumber",mobileNumber.text.toString())
                    startActivity(intent)
                }
                MySingleton.getInstance(this@ForgotPasswordActivity).addToRequestQueue(jsonObjectRequest)
            }
        }
    }

    override fun onStop() {
        MySingleton.getInstance(this@ForgotPasswordActivity).cancelAll("forgot_password")
        super.onStop()
    }
}