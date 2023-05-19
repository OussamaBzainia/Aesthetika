package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Post(

    var _id : String,
    var description: String,
    var image: String,
    var userId: User,
    var createdAt: Date,
    var likes:Array<String>,
    var isLiked:Boolean
) : Serializable
