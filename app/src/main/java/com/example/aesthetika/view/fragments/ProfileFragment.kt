package com.example.aesthetika.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.postViewModel
import com.example.aesthetika.ViewModel.userViewModel
import com.example.aesthetika.adapters.PostsAdapter
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.Post
import com.example.aesthetika.view.auth.EditProfieActivity
import com.example.aesthetika.view.auth.SettingsActivity
import com.example.aesthetika.view.features.ConversationAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import org.json.JSONObject



class ProfileFragment : Fragment() {


    val UserViewModel = userViewModel()
    val PostViewModel=postViewModel()
    var posts = ArrayList<Post>()

    private lateinit var FullName:TextView
    private lateinit var UserName:TextView
    private lateinit var settings:ImageButton
    private lateinit var editProfile: Button
    private lateinit var profilePic:ImageView
    private lateinit var postsRV:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        FullName=view.findViewById(R.id.full_name_text_view)
        UserName=view.findViewById(R.id.username_post_one)
        settings = view.findViewById(R.id.settings)
        editProfile=view.findViewById(R.id.edit_profile_button)
        profilePic=view.findViewById(R.id.profile_picture_post_one)
        postsRV=view.findViewById(R.id.posts_recycler_view)

        //get id from shared pref
        val sharedPref = requireContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("USER_ID", "")

        if (id != null) {
            UserViewModel.getUserById(id){ response, code ->
                // Handle the response
                val jsonResponse = JSONObject(response.toString())
                val FullNameGet = jsonResponse.getString("FullName")
                println(FullNameGet)
                val UsernameGet = jsonResponse.getString("username")
                val ProfileGet=jsonResponse.getString("ProfilePic")


                FullName.setText(FullNameGet)
                UserName.setText(UsernameGet)

                Picasso.get().load(ProfileGet).into(profilePic)

            }
        }


        postsRV.layoutManager = LinearLayoutManager(context)
        val adapter = PostsAdapter(posts)
        postsRV.adapter = adapter

        if (id != null) {
            PostViewModel.getPostsById(id) { response, code ->

                if (code == 200) {
                    val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                    posts = Gson().fromJson(jsonObject.getAsJsonArray("posts"), object : TypeToken<ArrayList<Post>>() {}.type)

                    adapter.posts=posts
                    adapter.notifyDataSetChanged()

                } else {
                    Log.e("ERROR", "Error: API call failed")

                }

            }
        }







        settings.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        editProfile.setOnClickListener {
            val intent=Intent(context,EditProfieActivity::class.java)
            startActivity(intent)
        }

        // Add the following code snippet here
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setCustomView(R.layout.actionbar_title)
        actionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Profile"

        return view
    }


}