package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class Conversation(
    @SerializedName("lastMessage")
    var lastMessage: String,
    @SerializedName("lastMessageDate")
    var lastMessageDate: Date,
    @SerializedName("sender")
    var sender: User,
    @SerializedName("receiver")
    var receiver: User,
)
