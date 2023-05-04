package com.example.aesthetika.utils

import com.example.aesthetika.model.entities.User
import com.google.gson.JsonElement
import com.google.gson.JsonObject
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
    @PUT("/updatePhoto/{id}")
    fun updatePhoto(@Path("id") _id: String,@Body requestBody: RequestBody): Call<JsonElement>
    @PUT("MiniProjet/UpdateArtistById/{id}")
    fun updateArtistById(@Path("id") _id: String,@Body requestBody: JsonObject): Call<JsonElement>


}