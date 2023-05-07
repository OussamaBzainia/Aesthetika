package com.example.aesthetika.view.features

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.view.auth.EmailVerificationActivity
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConversationAdapter(var conversations: List<Conversation>) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.conversation_item, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindView(conversations[position])
    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    inner class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImageView: ImageView = itemView.findViewById(R.id.profile_image_conversation)
        private val usernameTextView: TextView = itemView.findViewById(R.id.user_name_conversation)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.last_message)
        private val lastMessageTimeTextView: TextView = itemView.findViewById(R.id.last_message_time)

        fun bindView(conversation: Conversation) {

            /*itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("conversation", conversation)
                itemView.context.startActivity(intent)

            }*/

            usernameTextView.text=conversation.receiver.FullName
            lastMessageTextView.text=conversation.lastMessage




        }

    }
}
