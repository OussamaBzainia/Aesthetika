package com.example.aesthetika.ViewModel

import androidx.lifecycle.ViewModel
import com.example.aesthetika.utils.RetrofitInstance
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class userViewModel(): ViewModel() {

    // Create a Retrofit instance
    private val retrofit = RetrofitInstance.retrofit

    // Create an instance of your API service interface
    private val apiService = retrofit.create(com.example.aesthetika.utils.APIServices::class.java)

    // Create a function to make the register API call
    fun register(jsonObject: JsonObject, callback: (String?, Int?) -> Unit) {
        apiService.register(jsonObject).enqueue(object : Callback<JsonElement> {
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

    fun login(jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.login(jsonObject).enqueue(object : Callback<JsonElement> {
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

    fun verify(jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.verify(jsonObject).enqueue(object : Callback<JsonElement> {
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

    fun getOTP(jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.getOTP(jsonObject).enqueue(object : Callback<JsonElement> {
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

    fun resetPassword(jsonObject: JsonObject, callback: (String?, Int) -> Unit) {
        apiService.resetPassword(jsonObject).enqueue(object : Callback<JsonElement> {
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
    fun getUserById(id: String, callback: (String?, Int) -> Unit) {
        apiService.getUserById(id).enqueue(object : Callback<JsonElement> {
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

    fun updatePhoto(id: String, callback: (String?, Int) -> Unit) {
        apiService.updatePhoto(id).enqueue(object : Callback<JsonElement> {
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