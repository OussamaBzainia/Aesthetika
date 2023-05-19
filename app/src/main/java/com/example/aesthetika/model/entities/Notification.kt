package com.example.aesthetika.model.entities

import java.io.Serializable
import java.util.*

data class Notification(
    val userId: String,
    val message: String,
    val likedBy:User,
    val createdAt: Date
) : Serializable

