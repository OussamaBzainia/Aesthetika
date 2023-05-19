package com.example.aesthetika.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.chatViewModel
import com.example.aesthetika.ViewModel.userViewModel
import com.example.aesthetika.adapters.UserAdapter
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.User
import com.example.aesthetika.view.features.ConversationAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


class ChatFragment : Fragment() {

    val ChatViewModel= chatViewModel()
    val UserViewModel= userViewModel()

    var conversations = ArrayList<Conversation>()
    var users = ArrayList<User>()


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

        val userRecycler = rootView.findViewById<RecyclerView>(R.id.users_recyclerview)
        userRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter2 = UserAdapter(users)
        userRecycler.adapter = adapter2

        val sharedPrefs = context?.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val senderId = sharedPrefs?.getString("USER_ID","null")
        Log.d("id","$senderId")

        ChatViewModel.getMyConversations(senderId.toString()) { response, code ->

            if (code == 200) {
                val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                conversations = Gson().fromJson(jsonObject.getAsJsonArray("conversations"), object : TypeToken<ArrayList<Conversation>>() {}.type)

                adapter.conversations=conversations
                adapter.notifyDataSetChanged()
                Log.d("conversations","$conversations")
            } else {
                Log.e("ERROR", "Error: API call failed")

            }

        }

        UserViewModel.getAllUsers() { response, code ->

            if (code == 200) {
                val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                users = Gson().fromJson(jsonObject.getAsJsonArray("artists"), object : TypeToken<ArrayList<User>>() {}.type)

                adapter2.users=users
                adapter2.notifyDataSetChanged()
                Log.d("conversations","$users")
            } else {
                Log.e("ERROR", "Error: API call failed")

            }

        }


        // Add the following code snippet here
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setCustomView(R.layout.actionbar_title)
        actionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Chat"

        return rootView
    }


}