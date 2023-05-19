package com.example.aesthetika.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.postViewModel
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.User
import com.example.aesthetika.view.auth.EmailVerificationActivity
import com.example.aesthetika.view.features.ChatActivity
import com.example.aesthetika.view.features.ConversationAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter(var users: List<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    val PostViewModel=postViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        //shared pref
        val context = holder.itemView.context
        val sharedPref = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("USER_ID", "")

        val user=users[position]
        val destinationUser=user.id




        holder.usernameTextView.text = user.username // set username to receiver name

        val profileLinkImage= user.ProfilePic
        val pf=holder.profileImageView
        Picasso.get().load(profileLinkImage).into(pf)

        // add click listener to the conversation item
        holder.itemView.setOnClickListener {

            val jsonObject = JsonObject().apply {
                addProperty("sender", id)
                addProperty("receiver", destinationUser)
            }

            PostViewModel.createConversation(jsonObject){ response, code ->

                if (code == 200) {

                    showSnackbar(holder.itemView,"Conversation created", R.drawable.success)
                    Log.d("Conversation created", "Response: $response")

                } else {

                    Log.e("ERROR", "Error: API call failed")
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.profile_image_user)
        val usernameTextView: TextView = itemView.findViewById(R.id.username_user)




    }
    private fun showSnackbar(itemView: View,message: String, icon: Int) {
        val snackbar = Snackbar.make(itemView, message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundResource(if (icon == R.drawable.success) R.color.green else R.color.red)
        val iconView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
        val iconMargin = 20
        iconView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        iconView.compoundDrawablePadding = iconMargin
        snackbar.show()
    }




}