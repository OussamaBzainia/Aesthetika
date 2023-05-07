package com.example.aesthetika.view.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aesthetika.R

class ChatActivity : AppCompatActivity() {
    //ar messagesList: List<MessageWithoutPopulate> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }
}