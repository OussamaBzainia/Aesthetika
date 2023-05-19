package com.example.aesthetika.ViewModel

import androidx.lifecycle.ViewModel
import com.example.aesthetika.utils.RetrofitInstance
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class postViewModel: ViewModel() {
    // Create a Retrofit instance
    private val retrofit = RetrofitInstance.retrofit

    // Create an instance of your API service interface
    private val apiService = retrofit.create(com.example.aesthetika.utils.APIServices::class.java)

    fun addPost(description: RequestBody,userId: RequestBody, image: MultipartBody.Part, callback: (String?, Int?) -> Unit) {
        apiService.addPost(description,userId,image).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(),response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody,response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message,501)
            }
        })
    }

    fun getPostsById(id: String, callback: (String?, Int) -> Unit) {
        apiService.getPostsById(id).enqueue(object : Callback<JsonElement> {
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

    fun getAllPosts(callback: (String?, Int) -> Unit) {
        apiService.getAllPosts().enqueue(object : Callback<JsonElement> {
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

    fun likePost(id: String,jsonObject: JsonObject, callback: (String?, Int?) -> Unit) {
        apiService.likePost(id,jsonObject).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(),response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody,response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message,501)
            }
        })
    }

    fun createConversation(jsonObject: JsonObject, callback: (String?, Int?) -> Unit) {
        apiService.createConversation(jsonObject).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle the JSON response
                        callback(responseBody.toString(),response.code())
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    callback(errorBody,response.code())
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback(t.message,501)
            }
        })
    }



}