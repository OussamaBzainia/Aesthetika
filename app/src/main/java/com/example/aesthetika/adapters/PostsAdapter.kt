package com.example.aesthetika.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.model.entities.Post
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class PostsAdapter(var posts: List<Post>):
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_one_item, parent, false)
        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {


        val post=posts[position]
        val likesCount = post.likes.size
        Log.d("likes","$likesCount")


        holder.username.text = post.userId!!.username// set username
        holder.descriptionTextView.text = post.description // set description
        val formattedTime = formatDate(post.createdAt.toString())
        holder.time.text =formattedTime// set last message time
        holder.likes.text = "$likesCount likes" // set likes count
        val profileLinkImage= post.userId.ProfilePic
        val pf=holder.profileImage
        Picasso.get().load(profileLinkImage).into(pf)
        val postLinkImage= post.image
        val pf2=holder.postImage
        Picasso.get().load(postLinkImage).into(pf2)







    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profile_picture_post_one)
        val time:TextView=itemView.findViewById(R.id.time_post_one)
        val username: TextView = itemView.findViewById(R.id.username_post_one)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_post_one)
        val postImage: ImageView = itemView.findViewById(R.id.post_image_one)
        val likes: TextView = itemView.findViewById(R.id.likes_post_one)



    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE 'at' HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}