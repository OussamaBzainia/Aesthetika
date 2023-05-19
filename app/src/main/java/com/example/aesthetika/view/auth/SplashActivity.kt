package com.example.aesthetika.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.aesthetika.R
import io.socket.client.IO
import io.socket.client.Socket

class SplashActivity : AppCompatActivity() {


    private lateinit var SignIn:Button
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val getStarted = findViewById<Button>(R.id.GetStartedButton)

        SignIn=findViewById(R.id.ButtonsignIn)

        SignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        getStarted.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


    }


}