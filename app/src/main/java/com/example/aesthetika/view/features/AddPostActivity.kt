package com.example.aesthetika.view.features


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.postViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import org.json.JSONObject

class AddPostActivity : AppCompatActivity() {

    val PostViewModel = postViewModel()

    private lateinit var Description :EditText
    private lateinit var image :ImageView
    private lateinit var addPost : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        Description = findViewById(R.id.description)
        image = findViewById(R.id.image)
        addPost = findViewById(R.id.addPost)

        addPost.setOnClickListener {
            val description = Description.text.toString()
            //val filePart = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody(MultipartBody.FORM))


            // Retrieve the user ID from the SharedPreferences
            val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val id = sharedPref.getString("USER_ID", "")

            val jsonObject = JsonObject().apply {
                addProperty("description", description)
                addProperty("userId", id)

            }

            PostViewModel.addPost(jsonObject){ response, code ->
                if (code == 200) {
                    Log.d("SUCCESS", "Response: $response")

                    // Show a success message to the user
                    showSnackbar("Post added ", R.drawable.success)
                }
                else {
                    showSnackbar("Something went wrong, Please try again", R.drawable.error)
                    Log.e("ERROR", "Error: API call failed")
                }


            }
        }


    }
    private fun showSnackbar(message: String, icon: Int) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundResource(if (icon == R.drawable.success) R.color.green else R.color.red)
        // Add an icon to the right of the Snackbar message
        val iconView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
        val iconMargin = 20
        iconView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        iconView.compoundDrawablePadding = iconMargin
        snackbar.show()
    }
}