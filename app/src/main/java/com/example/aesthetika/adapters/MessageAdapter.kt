package com.example.aesthetika.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.MessageWithoutPopulate
import com.example.aesthetika.view.features.ChatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(var items: MutableList<MessageWithoutPopulate>, var conversation: Conversation) :
    RecyclerView.Adapter<MessageAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindView(items[position], conversation)
    }

    override fun getItemCount(): Int = items.size

    class ConversationViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val lastMessageTV: TextView = itemView.findViewById(R.id.descriptionTV)
        private val profilePictureIV: ImageView = itemView.findViewById(R.id.profilePictureIV)
        private val userNameTV: TextView = itemView.findViewById(R.id.userNameTV)

        fun bindView(message: MessageWithoutPopulate, conversation: Conversation) {

            //shared pref
            val sharedPref = itemView.context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val id = sharedPref.getString("USER_ID", "")


            if (message.senderConversation!!.sender == id) {
                (itemView as LinearLayout).gravity = Gravity.END

                userNameTV.text = conversation.sender!!.username

                val profileLinkImage= conversation.sender.ProfilePic

                Picasso.get().load(profileLinkImage).into(profilePictureIV)

            } else {
                userNameTV.text = conversation.receiver!!.username
                val profileLinkImage= conversation.receiver.ProfilePic

                Picasso.get().load(profileLinkImage).into(profilePictureIV)


            }

            lastMessageTV.text = message.description

        }
    }
}