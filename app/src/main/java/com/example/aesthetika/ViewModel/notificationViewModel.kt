package com.example.aesthetika.ViewModel

import androidx.lifecycle.ViewModel
import com.example.aesthetika.utils.RetrofitInstance
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class notificationViewModel: ViewModel() {  // Create a Retrofit instance
    private val retrofit = RetrofitInstance.retrofit

    // Create an instance of your API service interface
    private val apiService = retrofit.create(com.example.aesthetika.utils.APIServices::class.java)

    fun getNotifications(userId: String, callback: (String?, Int) -> Unit) {
        apiService.getNotifications(userId).enqueue(object : Callback<JsonElement> {
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