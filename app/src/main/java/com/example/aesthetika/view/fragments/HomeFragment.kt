package com.example.aesthetika.view.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.postViewModel
import com.example.aesthetika.adapters.PostsAdapter
import com.example.aesthetika.adapters.PostsAllAdapter
import com.example.aesthetika.model.entities.Post
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken


class HomeFragment : Fragment() {
    val PostViewModel= postViewModel()
    var posts = ArrayList<Post>()
    private lateinit var postsRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        postsRV=view.findViewById(R.id.post_all_recycler_view)

        val divider = ContextCompat.getDrawable(requireContext(), R.drawable.divider_gray)
        val itemDecoration = NotificationsFragment.GrayDividerItemDecoration(divider!!)
        postsRV.addItemDecoration(itemDecoration)

        //get id from shared pref
        val sharedPref = requireContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("USER_ID", "")

        postsRV.layoutManager = LinearLayoutManager(context)
        val adapter = PostsAllAdapter(posts)
        postsRV.adapter = adapter


            PostViewModel.getAllPosts{ response, code ->
                if (code == 200) {
                    val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                    posts = Gson().fromJson(jsonObject.getAsJsonArray("posts"), object : TypeToken<ArrayList<Post>>() {}.type)
                    Log.e("Posts", "$posts")
                    adapter.posts=posts
                    adapter.notifyDataSetChanged()

                } else {
                    Log.e("ERROR", "Error: API call failed")

                }


        }


        // Add the following code snippet here
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setCustomView(R.layout.actionbar_title)
        actionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "News Feed"


        return view



    }

    class GrayDividerItemDecoration(private val divider: Drawable) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            if (position > 0) {
                outRect.top = divider.intrinsicHeight
            }
        }

        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val dividerLeft = parent.paddingLeft
            val dividerRight = parent.width - parent.paddingRight

            val childCount = parent.childCount
            for (i in 1 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop = child.top - params.topMargin - divider.intrinsicHeight
                val dividerBottom = child.top - params.topMargin
                divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                divider.draw(canvas)
            }
        }
    }



}