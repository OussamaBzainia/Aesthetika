package com.example.aesthetika.model.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    var id: String,
    @SerializedName("FullName")
    var FullName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("mdp")
    var mdp: String,
    @SerializedName("PhoneNumber")
    var PhoneNumber: Int,
    @SerializedName("ProfilePic")
    var ProfilePic: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("BirthDate")
    var BirthDate: String,
)
