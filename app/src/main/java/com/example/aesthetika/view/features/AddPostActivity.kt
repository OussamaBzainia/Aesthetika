package com.example.aesthetika.view.features


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.postViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class AddPostActivity : AppCompatActivity() {

    val PostViewModel = postViewModel()
    private var imagePart: MultipartBody.Part? = null



    private lateinit var Description :EditText
    private lateinit var image :ImageView
    private lateinit var addPost : Button
    private lateinit var selectPhoto:Button
    private lateinit var video:VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        Description = findViewById(R.id.description)
        image = findViewById(R.id.image)
        addPost = findViewById(R.id.addPost)
        selectPhoto=findViewById(R.id.select_photo_add_post)
        video=findViewById(R.id.videoView)

        selectPhoto.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.accent)));
        image.setVisibility(View.GONE);

        selectPhoto.setOnClickListener {

            // Open the gallery app to select a photo
            val intentPhoto = Intent(Intent.ACTION_PICK)
            intentPhoto.type = "image/*,video/*"
            startActivityForResult(intentPhoto, 0)
        }


        addPost.setOnClickListener {
            val description = Description.text.toString()

            // Retrieve the user ID from the SharedPreferences
            val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val id = sharedPref.getString("USER_ID", "")

            val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
            val userIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), id!!)


            // Add the image part to the request body
            imagePart?.let { imagePart ->
                PostViewModel.addPost(descriptionBody,userIdBody, imagePart) { response, code ->
                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")

                        // Show a success message to the user
                        showSnackbar("Post added ", R.drawable.success)

                    } else {
                        showSnackbar("Something went wrong, Please try again", R.drawable.error)
                        Log.e("ERROR", "Error: API call failed")
                    }
                }
            } ?: run {
                // Show an error message to the user if no image is selected
                showSnackbar("Please select an image", R.drawable.error)
            }
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            val mediaType = contentResolver.getType(imageUri!!)
            image.setImageURI(imageUri)
            image.visibility = View.VISIBLE

            // Create a file request body from the selected image file
            val imageFile = File(getRealPathFromURI(imageUri))

            val imageRequestBody = RequestBody.create(mediaType?.toMediaTypeOrNull(), imageFile)
            imagePart = MultipartBody.Part.createFormData("ProfilePic", imageFile.name, imageRequestBody)
        }
    }
    private fun getRealPathFromURI(uri: Uri?): String {
        val cursor = contentResolver.query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        val path = cursor.getString(idx)
        cursor.close()
        return path
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