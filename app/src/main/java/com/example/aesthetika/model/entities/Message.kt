package com.example.aesthetika.model.entities

import java.io.Serializable
import java.util.*


    data class Message(
val _id: String,
val description: String,
val date: Date,
val senderConversation: Conversation?,
val receiverConversation: Conversation?
) : Serializable


