package com.example.aesthetika.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var FullName:EditText
    private lateinit var Email:EditText
    private lateinit var UserName:EditText
    private lateinit var Password:EditText
    private lateinit var SignUpButton:Button
    private lateinit var accept:CheckBox

    val UserViewModel = userViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
        supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Sign up"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        FullName=findViewById(R.id.FullName)
        Email=findViewById(R.id.EmailAdress)
        UserName=findViewById(R.id.UserName)
        Password=findViewById(R.id.PasswordSignUp)
        SignUpButton=findViewById(R.id.SignUpButton)
        accept=findViewById(R.id.acceptTerms)

        SignUpButton.setOnClickListener {
            val fullName = FullName.text.toString()
            val userName = UserName.text.toString()
            val email = Email.text.toString()
            val password = Password.text.toString()

            // Check that the email and password fields are not empty
            if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || userName.isEmpty() || !accept.isChecked) {
                // Show an error message to the user
                showSnackbar("All fields are required", R.drawable.error)
            }

            // Check that the email address is valid
            else if (!isValidEmail(email)) {
                // Show an error message to the user
                showSnackbar("Please enter a valid email address", R.drawable.error)
            }

            else {
                val emailLowerCase = email.trim().lowercase()

                // signup logic
                val jsonObject = JsonObject().apply {
                    addProperty("FullName", fullName)
                    addProperty("username", userName)
                    addProperty("email", emailLowerCase)
                    addProperty("mdp", password)
                }

                UserViewModel.register(jsonObject){ response, code ->

                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")
                        val jsonResponse = JSONObject(response)
                        val id = jsonResponse.getString("_id")
                        val emailExtracted = jsonResponse.getString("email")

                        showSnackbar("Sign up successful", R.drawable.success)

                        val handler = Handler()
                        handler.postDelayed({
                            val intent = Intent(this, EmailVerificationActivity::class.java)
                            intent.putExtra("emailExtracted", emailExtracted)
                            startActivity(intent)
                        }, 3000)
                    } else {
                        showSnackbar("Email already exists", R.drawable.error)
                        Log.e("ERROR", "Error: API call failed")
                    }
                }
            }
        }
    }

    // Function to check if an email address is valid
    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
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