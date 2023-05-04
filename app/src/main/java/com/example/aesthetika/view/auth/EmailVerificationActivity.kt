package com.example.aesthetika.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject

class EmailVerificationActivity : AppCompatActivity() {
    val UserViewModel = userViewModel()
    private lateinit var OTP:EditText
    private lateinit var VerifyButton:Button
    private lateinit var emailExtracted: String

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)

        OTP=findViewById(R.id.otpVerification)
        VerifyButton=findViewById(R.id.VerifyEmail)

        emailExtracted = intent.getStringExtra("emailExtracted").toString()

        VerifyButton.setOnClickListener {
            val otp = OTP.text.toString()

            if (otp.isEmpty()) {
                // Show an error message to the user
                showSnackbar("All fields are required", R.drawable.error)
            }
            else if (otp.length !=6) {
                // Show an error message to the user
                showSnackbar("OTP must be 6 characters", R.drawable.error)
            }
            else {
                // email verification logic
                val jsonObject = JsonObject().apply {
                    addProperty("otp", otp)
                    addProperty("email", emailExtracted)
                }

                UserViewModel.verify(jsonObject){ response, code ->

                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")
                        showSnackbar("Account verified successfuly", R.drawable.success)
                        val handler = Handler()
                        handler.postDelayed({
                            val intent = Intent(this, SignInActivity::class.java)
                             startActivity(intent)
                        }, 2000)

                    } else {
                        showSnackbar("Invalid or expired OTP ", R.drawable.error)
                        Log.e("ERROR", "Error: API call failed")
                    }
                }
            }


        }

    }
    // Function to display a snackbar message
    private fun showSnackbar(message: String, icon: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundResource(if (icon == R.drawable.success) R.color.green else R.color.red)
        // Add an icon to the right of the Snackbar message
        val iconView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
        val iconMargin = 20
        iconView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        iconView.compoundDrawablePadding = iconMargin
        snackbar.show()
    }
}