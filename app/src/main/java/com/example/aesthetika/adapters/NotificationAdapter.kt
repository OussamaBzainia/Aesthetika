package com.example.aesthetika.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.Notification
import com.example.aesthetika.view.features.ChatActivity
import com.example.aesthetika.view.features.ConversationAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(var notifications: List<Notification>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {


        val notification=notifications[position]


        holder.usernameTextView.text = notification.likedBy.username // set username to receiver name
        holder.message.text = notification.message // set last message text
        val createdAt = notification.createdAt
        val formattedTime = formatDate(createdAt.toString())
        holder.time.text = formattedTime // set formatted time
        val profileLinkImage= notification.likedBy.ProfilePic
        val pf=holder.profileImageView
        Picasso.get().load(profileLinkImage).into(pf)


    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.profile_image_notification)
        val usernameTextView: TextView = itemView.findViewById(R.id.username_notification)
        val message: TextView = itemView.findViewById(R.id.message_notification)
        val time: TextView = itemView.findViewById(R.id.time_notification)


    }
    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE 'at' HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }



}