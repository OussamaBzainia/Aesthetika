package com.example.aesthetika.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.example.aesthetika.view.features.UserNav
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    val UserViewModel = userViewModel()

    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var SignInButton: Button
    private lateinit var ForgotButton : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
        supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Sign In"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Get references to the views
        Email = findViewById(R.id.Email)
        Password = findViewById(R.id.Password)
        SignInButton = findViewById(R.id.button)
        ForgotButton=findViewById(R.id.forgotPassword)

        // Set click listener for the sign in button
        SignInButton.setOnClickListener {
            val email = Email.text.toString()
            val password = Password.text.toString()

            // Check that the email and password fields are not empty
            if (email.isEmpty() || password.isEmpty()) {
                // Show an error message to the user
                showSnackbar("All fields are required", R.drawable.error)
            }

            // Check that the email address is valid
            else if (!isValidEmail(email)) {
                // Show an error message to the user
                showSnackbar("Please enter a valid email address", R.drawable.error)
            }

            else {
                val emailLowerCase = email.lowercase()

                val jsonObject = JsonObject().apply {
                    addProperty("email", emailLowerCase)
                    addProperty("mdp", password)
                }

                UserViewModel.login(jsonObject) { response, code ->

                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")

                        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                        val jsonResponse = JSONObject(response.toString())
                        val id = jsonResponse.getString("_id")
                        val FullName = jsonResponse.getString("FullName")
                        val ProfilePic = jsonResponse.getString("ProfilePic")
                        val token = jsonResponse.getString("token")

                        // Save the values in the shared preferences
                        val editor = sharedPref.edit()
                        editor.putString("USER_ID", id)
                        editor.putString("FULL_NAME", FullName)
                        editor.putString("Profil_Pic", ProfilePic)
                        editor.putString("TOKEN", token)
                        editor.apply()


                        // Show a success message to the user
                        showSnackbar("Welcome back", R.drawable.success)

                        val handler = Handler()
                        handler.postDelayed({
                            val intent = Intent(this, UserNav::class.java)
                            startActivity(intent)
                        }, 2000)
                    }
                    else {
                        showSnackbar("Wrong information, Please try again", R.drawable.error)
                        Log.e("ERROR", "Error: API call failed")
                    }
                }


            }
        }

        // Set click listener for the forgot password button
        ForgotButton.setOnClickListener {
            val intent = Intent(this, PassowrdResetFirstActivity::class.java)
            startActivity(intent)
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

    /*private fun showSnackbar(message: String, icon: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundResource(if (icon == R.drawable.success) R.color.green else R.color.red)

        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)

        snackbar.show()
    }*/

}
