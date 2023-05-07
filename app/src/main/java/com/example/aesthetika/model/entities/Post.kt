package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("description")
    var description: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("userId")
    var userId: String,

)
