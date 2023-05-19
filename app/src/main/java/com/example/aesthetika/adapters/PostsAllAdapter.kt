package com.example.aesthetika.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.postViewModel
import com.example.aesthetika.model.entities.Post
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PostsAllAdapter(var posts: List<Post>) :
    RecyclerView.Adapter<PostsAllAdapter.PostsAllViewHolder>() {
    private lateinit var socket: Socket
    init {
        try {
            val opts = IO.Options()
            opts.forceNew = true
            socket = IO.socket("http://192.168.1.3:9090/", opts)
            socket.connect()
            socket.on(Socket.EVENT_CONNECT) {
                println("Socket Connected")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    val PostViewModel= postViewModel()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAllViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_all_item, parent, false)
        return PostsAllViewHolder(view)
    }


    override fun onBindViewHolder(holder: PostsAllViewHolder, position: Int) {


        //get id from shared pref
        val context = holder.itemView.context
        val sharedPref = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("USER_ID", "")

        val post=posts[position]
        var likesCount = post.likes.size
        Log.d("likes","$likesCount")

        if (post.isLiked){
            holder.likeButton.setImageDrawable(
                holder.itemView.context.getDrawable(R.drawable.outline_favorite_24))
        }



        val postId=post._id
        holder.username.text = post.userId.username// set username
        holder.descriptionTextView.text = post.description // set description
        val formattedTime = formatDate(post.createdAt.toString())
        holder.time.text = formattedTime// set last message time
        holder.likes.text = "$likesCount " // set likes count
        val profileLinkImage= post.userId.ProfilePic
        val pf=holder.profileImage
        Picasso.get().load(profileLinkImage).into(pf)
        val postLinkImage= post.image
        val pf2=holder.postImage
        Picasso.get().load(postLinkImage).into(pf2)



        holder.likeButton.setOnClickListener{
            val jsonObject = JsonObject().apply {
                addProperty("userId", id as String)
            }
            socket.emit("likePost", jsonObject)


            PostViewModel.likePost(postId,jsonObject){ response, code ->
                if (code == 200) {
                    val message = response.toString()

                    if (message == "The post has been liked") {
                        holder.likeButton.setImageDrawable(
                            holder.itemView.context.getDrawable(R.drawable.outline_favorite_border_24))
                        holder.likes.text = "${likesCount + 1} "
                        notifyDataSetChanged()
                        //emit socket event

                    } else if (message == "The post has been disliked") {
                        holder.likeButton.setImageDrawable(
                            holder.itemView.context.getDrawable(R.drawable.outline_favorite_24))
                        holder.likes.text = "${likesCount} "
                        notifyDataSetChanged()
                    }

                    // Updated code to use setImageDrawable()
                    if (holder.likeButton.drawable.constantState ==
                        holder.itemView.context.getDrawable(R.drawable.outline_favorite_24)?.constantState) {
                        holder.likeButton.setImageDrawable(
                            holder.itemView.context.getDrawable(R.drawable.outline_favorite_border_24))
                    } else {
                        holder.likeButton.setImageDrawable(
                            holder.itemView.context.getDrawable(R.drawable.outline_favorite_24))
                    }

                } else {
                    Log.e("ERROR", "Error: API call failed")
                }
            }
        }






    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostsAllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profile_picture_home)
        val time: TextView =itemView.findViewById(R.id.post_date_home)
        val username: TextView = itemView.findViewById(R.id.user_name_home)
        val descriptionTextView: TextView = itemView.findViewById(R.id.post_description_home)
        val postImage: ImageView = itemView.findViewById(R.id.post_image_home)
        val likes: TextView = itemView.findViewById(R.id.like_count_home)
        val likeButton : ImageView=itemView.findViewById(R.id.like_button)





    }
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        // Disconnect from the server socket when the adapter is detached
        socket.disconnect()
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE 'at' HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }


}