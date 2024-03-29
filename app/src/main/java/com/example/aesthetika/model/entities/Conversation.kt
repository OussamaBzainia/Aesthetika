package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Conversation(
    val _id: String,
    val lastMessage: String,
    val lastMessageDate: Date,
    val sender: User?,
    val receiver: User?
) : Serializable
