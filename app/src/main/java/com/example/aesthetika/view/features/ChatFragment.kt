package com.example.aesthetika.view.features

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.chatViewModel
import com.example.aesthetika.model.entities.Conversation
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


class ChatFragment : Fragment() {

    val ChatViewModel= chatViewModel()
    var conversations = ArrayList<Conversation>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_chat, container, false)

        val conversationRecycler = rootView.findViewById<RecyclerView>(R.id.conversations_recyclerview)
        conversationRecycler.layoutManager = LinearLayoutManager(context)
        val adapter = ConversationAdapter(conversations)
        conversationRecycler.adapter = adapter

        val sharedPrefs = context?.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val senderId = sharedPrefs?.getString("USER_ID","null")

        ChatViewModel.getMyConversations(senderId.toString()) { response, code ->

            if (code == 200) {
                val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                conversations = Gson().fromJson(jsonObject.getAsJsonArray("conversations"), object : TypeToken<ArrayList<Conversation>>() {}.type)

            } else {
                Log.e("ERROR", "Error: API call failed")

            }

        }











        return rootView
    }


}