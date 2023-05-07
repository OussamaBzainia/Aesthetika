package com.example.aesthetika.view.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.aesthetika.R
import com.example.aesthetika.view.features.UserNav
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var darkMode:Switch
    private lateinit var signOut:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
        supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Settings"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        darkMode=findViewById(R.id.darkMode)
        signOut=findViewById(R.id.SignOut)

        darkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // User enabled dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // User disabled dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }




        signOut.setOnClickListener {
            showSnackbar("See you next time",R.drawable.success)

            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }, 1500)
        }

    }

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