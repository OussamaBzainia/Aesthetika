package com.example.aesthetika.view.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.aesthetika.R
import com.example.aesthetika.ViewModel.userViewModel
import com.example.aesthetika.view.auth.EditProfieActivity
import com.example.aesthetika.view.auth.SettingsActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val UserViewModel = userViewModel()


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var FullName:TextView
    private lateinit var UserName:TextView
    private lateinit var settings:ImageButton
    private lateinit var editProfile: Button
    private lateinit var profilePic:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        FullName=view.findViewById(R.id.full_name_text_view)
        UserName=view.findViewById(R.id.username_text_view)
        settings = view.findViewById(R.id.settings)
        editProfile=view.findViewById(R.id.edit_profile_button)
        profilePic=view.findViewById(R.id.profile_picture_image_view)

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





        settings.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        editProfile.setOnClickListener {
            val intent=Intent(context,EditProfieActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}