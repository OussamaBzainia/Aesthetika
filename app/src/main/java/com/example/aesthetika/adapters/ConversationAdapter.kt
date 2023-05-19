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
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ConversationAdapter(var conversations: List<Conversation>) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.conversation_item, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {


            val conversation=conversations[position]

            val userr=conversation.receiver!!.username
            holder.usernameTextView.text = userr // set username to receiver name

            val profileLinkImage= conversation.receiver.ProfilePic
            val pf=holder.profileImageView
            Picasso.get().load(profileLinkImage).into(pf)


            val lastMessage=conversation.lastMessage
            holder.lastMessageTextView.text = lastMessage // set last message text
            holder.lastMessageTimeTextView.text =
                formatDate(conversation.lastMessageDate.toString()) // set last message time

          // add click listener to the conversation item
           holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("conversation", conversation)
            holder.itemView.context.startActivity(intent)
           }


    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val profileImageView: ImageView = itemView.findViewById(R.id.profile_image_conversation)
         val usernameTextView: TextView = itemView.findViewById(R.id.user_name_conversation)
         val lastMessageTextView: TextView = itemView.findViewById(R.id.last_message)
         val lastMessageTimeTextView: TextView = itemView.findViewById(R.id.last_message_time)


    }
    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

}
