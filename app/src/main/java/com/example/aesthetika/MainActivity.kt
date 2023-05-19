package com.example.aesthetika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.aesthetika.view.auth.SplashActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}