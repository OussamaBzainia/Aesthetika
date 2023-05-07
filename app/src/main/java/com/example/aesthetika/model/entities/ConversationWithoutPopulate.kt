package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class ConversationWithoutPopulate(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("lastMessage")
    val lastMessage: String,
    @SerializedName("lastMessageDate")
    val lastMessageDate: Date,
    @SerializedName("sender")
    val sender: String?,
    @SerializedName("receiver")
    val receiver: String?
)
