package com.example.aesthetika.view.features

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.chatViewModel
import com.example.aesthetika.adapters.MessageAdapter
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.MessageWithoutPopulate
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {

    val ChatViewModel=chatViewModel()

    var messagesList: ArrayList<MessageWithoutPopulate> = arrayListOf()



    private var chatRV: RecyclerView? = null
    var addButton: Button? = null
    var messageTIET: TextInputEditText? = null

    private var currentConversation: Conversation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //binding
        chatRV = findViewById(R.id.chatRV)
        addButton = findViewById(R.id.sendMessage)
        messageTIET = findViewById(R.id.messageTIET)

        //shared pref
        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("USER_ID", "")

        // receiver
        currentConversation = intent.getSerializableExtra("conversation") as Conversation?
        val currentUser = currentConversation!!.receiver


        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        chatRV!!.layoutManager = linearLayoutManager

        chatRV!!.adapter = MessageAdapter(
            messagesList,
            currentConversation!!
        )

        //show messages
        getData()
        startUpdates()


        //send message
        addButton!!.setOnClickListener {
            val jsonObject = JsonObject().apply {
                addProperty("description", messageTIET!!.text.toString())
                addProperty("senderId", id)
                addProperty("receiverId", currentUser!!.id)

            }
            ChatViewModel.sendMessage(jsonObject) { response, code ->

                if (code == 200) {
                    messageTIET!!.setText("")
                } else {
                    Log.e("ERROR", "Error: API call failed")

                }

            }
        }






    }

    private fun getData() {
        ChatViewModel.getMyMessages(currentConversation!!._id) { response, code ->
            if (code == 200) {
                val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                val messagesArray = jsonObject.getAsJsonArray("messages")
                if (messagesArray != null) {
                    messagesList = Gson().fromJson(messagesArray, object : TypeToken<ArrayList<MessageWithoutPopulate>>() {}.type)
                    Log.d("conversations","$messagesList")
                    chatRV!!.adapter = MessageAdapter(messagesList, currentConversation!!)
                    chatRV!!.scrollToPosition(messagesList.size - 1);
                } else {
                    Log.e("ERROR", "Error: messagesList is null")
                }
            } else {
                Log.e("ERROR", "Error: API call failed")
            }
        }
    }
    private val scope = MainScope() // could also use an other scope such as viewModelScope if available
    var job: Job? = null

    private fun startUpdates() {
        job = scope.launch {
            while (true) {
                getData() // the function that should be ran every second
                delay(2500)
            }
        }
    }

    private fun stopUpdates() {
        job?.cancel()
        job = null
    }

    override fun finish() {
        super.finish()
        stopUpdates()
    }

}