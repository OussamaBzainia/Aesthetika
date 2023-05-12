package com.example.aesthetika.utils

import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.MessageWithoutPopulate
import com.example.aesthetika.model.entities.User
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIServices {

    @POST("/MiniProjet/register")
    fun register(@Body requestBody: JsonObject): Call<JsonElement>
    @POST("/MiniProjet/login")
    fun login( @Body requestBody: JsonObject): Call<JsonElement>
    @POST("/MiniProjet/verify")
    fun verify( @Body requestBody: JsonObject): Call<JsonElement>
    @POST("/MiniProjet/getOTP")
    fun getOTP( @Body requestBody: JsonObject): Call<JsonElement>
    @POST("/MiniProjet/resetPassword")
    fun resetPassword( @Body requestBody: JsonObject): Call<JsonElement>
    @GET("/MiniProjet/{id}")
    fun getUserById(@Path("id") _id: String): Call<JsonElement>

    @Multipart
    @PUT("MiniProjet/updatePhoto/{id}")
    fun updatePhoto(@Path("id") _id: String,@Part filePart: MultipartBody.Part): Call<JsonElement>
    @PUT("MiniProjet/UpdateArtistById/{id}")
    fun updateArtistById(@Path("id") _id: String,@Body requestBody: JsonObject): Call<JsonElement>



    //Post
    @POST("/addPost")
    fun addPost(@Body requestBody: JsonObject): Call<JsonElement>



    //Chat

    @GET("/getMyConversations/{senderId}")
    fun getMyConversations(@Path("senderId") senderId: String): Call<JsonElement>
    @GET("/getMyMessages/{conversationId}")
    fun getMyMessages(@Path("conversationId") conversationId: String): Call<JsonElement>
    @POST("/sendMessage")
    fun sendMessage(@Body requestBody: JsonObject): Call<JsonElement>





}