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
import org.json.JSONObject

class PasswordResetSecondActivity : AppCompatActivity() {

    private lateinit var OtpReset:EditText
    private lateinit var NewPassword:EditText
    private lateinit var ConfirmPassword:EditText
    private lateinit var ResetButton: Button
    private lateinit var EmailExtracted: String

    val UserViewModel=userViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_second)

        OtpReset=findViewById(R.id.otpReset)
        NewPassword=findViewById(R.id.NewPassword)
        ConfirmPassword=findViewById(R.id.ConfirmPassword)
        ResetButton=findViewById(R.id.ResetPasswordSecond)

        EmailExtracted = intent.getStringExtra("EmailExtracted").toString()

        ResetButton.setOnClickListener {
            val otpReset = OtpReset.text.toString()
            val newPassword=NewPassword.text.toString()
            val confirmPassword=ConfirmPassword.text.toString()

            if (otpReset.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                // Show an error message to the user
                showSnackbar("All fields are required", R.drawable.error)
            }
            else if (newPassword !=confirmPassword) {
                // Show an error message to the user
                showSnackbar("Passwords should match", R.drawable.error)
            }
            else if (otpReset.length !=4){
                // Show an error message to the user
                showSnackbar("OTP must be exactly 4 characters", R.drawable.error)
            }
            else {
                // password reset second logic
                val jsonObject = JsonObject().apply {
                    addProperty("email", EmailExtracted)
                    addProperty("otpReset", otpReset)
                    addProperty("mdp", newPassword)
                }

                UserViewModel.resetPassword(jsonObject){ response, code ->

                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")
                        val jsonResponse = JSONObject(response)

                        showSnackbar("Password changed successfully", R.drawable.success)

                        val handler = Handler()
                        handler.postDelayed({
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }, 2000)
                    }
                    else {
                        showSnackbar("Invalid or expired OTP", R.drawable.error)
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