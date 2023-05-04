package com.example.aesthetika.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONObject

class PassowrdResetFirstActivity : AppCompatActivity() {

    private lateinit var Email:EditText
    private lateinit var ContinueResetButton:Button

    val UserViewModel= userViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passowrd_reset_first)

        Email=findViewById(R.id.EmailResetPasswordTextField)
        ContinueResetButton=findViewById(R.id.ContinueResetButton)



        ContinueResetButton.setOnClickListener {
            val email=Email.text.toString().lowercase()

            // Check that the email and password fields are not empty
            if (email.isEmpty()) {
                // Show an error message to the user
                showSnackbar("All fields are required", R.drawable.error)
            }

            // Check that the email address is valid
            else if (!isValidEmail(email)) {
                // Show an error message to the user
                showSnackbar("Please enter a valid email address", R.drawable.error)
            }
            else {
                // reset password first screen logic
                val jsonObject = JsonObject().apply {
                    addProperty("email", email)
                }

                UserViewModel.getOTP(jsonObject){ response, code ->

                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")
                        val jsonResponse = JSONObject(response)
                        val EmailExtracted = jsonResponse.getString("email")

                        showSnackbar("Reset OTP sent to the email", R.drawable.success)

                        val handler = Handler()
                        handler.postDelayed({
                            val intent = Intent(this, PasswordResetSecondActivity::class.java)
                            intent.putExtra("EmailExtracted", EmailExtracted)
                            startActivity(intent)
                        }, 2000)
                    } else {
                        showSnackbar("No account was found with that email", R.drawable.error)
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

    // Function to check if an email address is valid
    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}