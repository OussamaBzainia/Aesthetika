package com.example.aesthetika.view.auth

import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.example.aesthetika.view.features.UserNav
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody


class EditProfieActivity : AppCompatActivity() {

    private lateinit var FullName:EditText
    private lateinit var Username:EditText
    private lateinit var Update:Button
    private lateinit var ProfilePic:ImageView
    private lateinit var EditPhoto:ImageButton

    val UserViewModel = userViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setCustomView(R.layout.actionbar_title)
        supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)?.text = "Edit profile"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profie)

        FullName=findViewById(R.id.FullNameEdit)
        Username=findViewById(R.id.UsernameEdit)
        ProfilePic=findViewById(R.id.ProfilePicEditProfile)
        Update=findViewById(R.id.UpdateButton)
        EditPhoto=findViewById(R.id.EditPhoto)

        //get id from shared pref
        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPref.getString("USER_ID", "")
        val ProfilePicGet = sharedPref.getString("Profil_Pic", "")
        println(id)
        println(ProfilePicGet)

        if (id != null) {
            UserViewModel.getUserById(id){ response, code ->
                // Handle the response
                val jsonResponse = JSONObject(response.toString())
                val FullNameGet = jsonResponse.getString("FullName")
                println(FullNameGet)
                val UsernameGet = jsonResponse.getString("username")


                FullName.setText(FullNameGet)
                Username.setText(UsernameGet)
                Picasso.get().load(ProfilePicGet).into(ProfilePic)

            }
        }

        Update.setOnClickListener {
            val fullName = FullName.text.toString()
            val userName = Username.text.toString()

            if (fullName.isEmpty() || userName.isEmpty()) {
                // Show an error message to the user
                showSnackbar("Fields must not be empty", R.drawable.error)
            }

            val jsonObject = JsonObject().apply {
                addProperty("FullName", fullName)
                addProperty("username", userName)
            }

            if (id != null) {
                UserViewModel.updateArtistById(id,jsonObject){ response, code ->
                    if (code == 200) {
                        Log.d("SUCCESS", "Response: $response")
                        // Show a success message to the user
                        showSnackbar("Informations updated successfully", R.drawable.success)
                    }
                    else {
                        showSnackbar("Something went wrong, Please try again", R.drawable.error)
                        Log.e("ERROR", "Error: API call failed")
                    }

                }
            }

        }


        EditPhoto.setOnClickListener {
            // Open the gallery app to select a photo
            val intentPhoto = Intent(Intent.ACTION_PICK)
            intentPhoto.type = "image/*"
            startActivityForResult(intentPhoto, 0)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 0) {
            // Get the selected image's URI
            val uri = data?.data
            if (uri != null) {
                // Retrieve the user ID from the SharedPreferences
                val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                val id = sharedPref.getString("USER_ID", "")
                if (id != null) {
                    val file = File(getPathFromUri(uri))
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val filePart = MultipartBody.Part.createFormData("ProfilePic", file.name, requestFile)
                    // Call the updatePhoto API endpoint with the selected image file
                    UserViewModel.updatePhoto(id, filePart){ response, code ->
                        // Handle the response
                        val jsonResponse = JSONObject(response.toString())
                        val newURL = jsonResponse.getString("newURL")
                        // Update the profile picture
                        Picasso.get().load(newURL).into(ProfilePic)
                    }
                }
            }
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(column_index)
            cursor.close()
            return filePath
        }
        return null
    }





    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 0) {
            // Get the selected image's URI
            val uri = data?.data
            if (uri != null) {
                val file = File(getPathFromUri(uri))
                // Call the updatePhoto API endpoint with the selected image file
                UserViewModel.updatePhoto(id, file){ response, code ->
                    // Handle the response
                    val jsonResponse = JSONObject(response.toString())
                    val newURL = jsonResponse.getString("newURL")
                    // Update the profile picture
                    Picasso.get().load(newURL).into(ProfilePic)
                }
            }
        }*/

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