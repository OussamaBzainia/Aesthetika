package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MessageWithoutPopulate(
    val _id: String,
    val description: String,
    val date: Date,
    val senderConversation: ConversationWithoutPopulate?,
    val receiverConversation: ConversationWithoutPopulate?
) : Serializable