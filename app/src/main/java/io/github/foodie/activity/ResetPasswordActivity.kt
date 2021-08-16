package io.github.foodie.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import io.github.foodie.R
import io.github.foodie.api.ResetPassword
import io.github.foodie.util.ConnectionAlert
import io.github.foodie.util.SwrToast

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var otp: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var submit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val mobileNumber = intent?.getStringExtra("mobileNUmber") ?: kotlin.run {
            SwrToast.show(this@ResetPasswordActivity)
            ""
        }
        otp = findViewById(R.id.edtxtResetPasswordOTP)
        password = findViewById(R.id.edtxtResetPasswordPassword)
        confirmPassword = findViewById(R.id.edtxtResetPasswordConfirmPassword)
        submit = findViewById(R.id.btnResetPasswordSubmit)

        submit.setOnClickListener {
            if (password.text.toString() == confirmPassword.text.toString()) {
                if (ConnectionAlert(this@ResetPasswordActivity).start({
                        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                        finish()
                    }, {
                        ActivityCompat.finishAffinity(this@ResetPasswordActivity)
                    })) {
                    val jsonObjectRequest = ResetPassword(
                        this@ResetPasswordActivity,
                        mobileNumber,
                        otp.text.toString(),
                        confirmPassword.text.toString()
                    ).getRequest(
                        getSharedPreferences(
                            getString(R.string.sharedLoginFile),
                            MODE_PRIVATE
                        )
                    ) {
                        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(
                    this@ResetPasswordActivity,
                    getString(R.string.passwordsDontMatch),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}