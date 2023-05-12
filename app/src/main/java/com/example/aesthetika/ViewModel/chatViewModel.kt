package com.example.aesthetika.ViewModel

import androidx.lifecycle.ViewModel
import com.example.aesthetika.utils.RetrofitInstance
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class chatViewModel : ViewModel()  {
    private val retrofit = RetrofitInstance.retrofit

    // Create an instance of your API service interface
    private val apiService = retrofit.create(com.example.aesthetika.utils.APIServices::class.java)

    fun sendMessage(jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.sendMessage(jsonObject).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(), response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody, response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message, 501)
            }
        })
    }

    fun getMyConversations(senderId: String, callback: (String?, Int) -> Unit) {
        apiService.getMyConversations(senderId).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(), response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody, response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message, 501)
            }
        })
    }

    fun getMyMessages(conversationId: String, callback: (String?, Int) -> Unit) {
        apiService.getMyMessages(conversationId).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(), response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody, response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message, 501)
            }
        })
    }
}