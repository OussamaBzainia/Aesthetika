package com.example.aesthetika.view.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.notificationViewModel
import com.example.aesthetika.ViewModel.postViewModel
import com.example.aesthetika.adapters.NotificationAdapter
import com.example.aesthetika.model.entities.Conversation
import com.example.aesthetika.model.entities.Notification
import com.example.aesthetika.view.features.ConversationAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.socket.client.Socket
import io.socket.client.IO
import org.json.JSONArray
import org.json.JSONObject

class NotificationsFragment : Fragment() {

    val NotificationViewModel= notificationViewModel()
    var notifications = ArrayList<Notification>()

    private lateinit var socket: Socket


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        val notificationRecycler = view.findViewById<RecyclerView>(R.id.notifications_recyclerview)
        notificationRecycler.layoutManager = LinearLayoutManager(context)
        val adapter = NotificationAdapter(notifications)
        notificationRecycler.adapter = adapter

        val divider = ContextCompat.getDrawable(requireContext(), R.drawable.divider_gray)
        val itemDecoration = GrayDividerItemDecoration(divider!!)
        notificationRecycler.addItemDecoration(itemDecoration)

        val sharedPrefs = context?.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val userId = sharedPrefs?.getString("USER_ID","null")


            NotificationViewModel.getNotifications(userId.toString()) {  response, code ->

                if (code == 200) {
                    val jsonObject = Gson().fromJson(response, JsonObject::class.java)
                    notifications = Gson().fromJson(jsonObject.getAsJsonArray("notifications"), object : TypeToken<ArrayList<Notification>>() {}.type)

                    adapter.notifications=notifications
                    adapter.notifyDataSetChanged()

                } else {
                    Log.e("ERROR", "Error: API call failed")

                }

            }









        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setCustomView(R.layout.actionbar_title)
            actionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Notifications"

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


// Connect to the server socket
/* socket = IO.socket("http://192.168.1.3:9090/") // Replace with your server URL
 socket.connect()

 // Listen for the "notification" event
 socket.on("notification") { args ->
     val data = args[0] as JSONObject

     // Extract the message from the received data
     val message = data.getString("message")
     Log.d("message",message)

     // Display the notification to the user
     activity?.runOnUiThread {
         println(data)
         Log.d("Notification", "Received: $message") // Debug log
     }
 }*/