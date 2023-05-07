package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName

data class MessageWithoutPopulate(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("senderConversation")
    var senderConversation: ConversationWithoutPopulate,
    @SerializedName("receiverConversation")
    var receiverConversation: ConversationWithoutPopulate,
)
