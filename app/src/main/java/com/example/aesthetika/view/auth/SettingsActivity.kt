package com.example.aesthetika.view.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.aesthetika.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var darkMode:Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
        supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Settings"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        darkMode=findViewById(R.id.darkMode)

        darkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // User enabled dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // User disabled dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            darkMode.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkMode.isChecked = false
        }



    }
}