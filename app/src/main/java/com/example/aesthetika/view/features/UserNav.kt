package com.example.aesthetika.view.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.aesthetika.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserNav : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_nav)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        fab=findViewById(R.id.fab)

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()

        fab.setOnClickListener{
                val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.chat -> {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, ChatFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notifications -> {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, NotificationsFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }



    }
}