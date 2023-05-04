package com.example.aesthetika.view.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.squareup.picasso.Picasso
import org.json.JSONObject

class EditProfieActivity : AppCompatActivity() {

    private lateinit var FullName:EditText
    private lateinit var Username:EditText
    private lateinit var Update:Button
    private lateinit var ProfilePic:ImageView
    private lateinit var EditPhoto:ImageButton

    val UserViewModel = userViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
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

        }


        EditPhoto.setOnClickListener {
            // Open the gallery app to select a photo
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

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



}