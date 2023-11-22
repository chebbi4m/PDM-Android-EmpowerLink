package tn.esprit.pdm.uikotlin.searchuser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import tn.esprit.pdm.R
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.searchuser.UserAdapter
import tn.esprit.pdm.utils.Apiuser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.databinding.ActivityProfileVisitBinding



class VisitProfile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileVisitBinding
    private lateinit var apiuser: Apiuser
    private lateinit var visitedUserId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileVisitBinding.inflate(layoutInflater)
        setContentView(binding.root)
binding.tvFullname.setOnClickListener(){

    followUser()
}
        apiuser = Apiuser.create()
        //visitedUserId = intent.getStringExtra() ?: ""
        // Get the visited user from the intent
       // val visitedUser: LoginRequest? = intent.getSerializableExtra(UserAdapter.EXTRA_USER) as? LoginRequest

        // Use the data of the visited user to display the profile

            val username = getUsernameFromSharedPreferences()

            // Call the getUserByName method to get the user details by username
            apiuser.getUserByName(username).enqueue(object : Callback<LoginRequest> {
                override fun onResponse(call: Call<LoginRequest>, response: Response<LoginRequest>) {
                    if (response.isSuccessful) {
                        val user = response.body()

                        user?.let {
                            val fullname = user.firstname
                            val speciality = user.adress
                            val address = user.number
                            val imageUrl = user.image
                            val skills = user.skills?.joinToString(", ")

                            // Configure your views to display the user details
                            binding.tvFullname.text = fullname
                            binding.tvSpeciality.text = speciality
                            binding.tvAddress.text = address
                            binding.Skills.text = skills
                            Glide.with(this@VisitProfile)
                                .load(imageUrl)
                                .error(R.drawable.ic_account) // Optional error image
                                .into(binding.ivPic)

                            //iv_pic
                        }
                    } else {
                        // Handle response errors
                    }
                }

                override fun onFailure(call: Call<LoginRequest>, t: Throwable) {
                    // Handle request errors
                }
            })
        }
    private fun getUsernameFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("username", "") ?: ""
    }
    private fun followUser() {
        val userId = "6550bce0a0d1a744ea94d641"
        // Create a FollowRequest object with the visited user's ID
        val followRequest = LoginRequest(userId = userId)

        // Call the followUser endpoint
        apiuser.followUser(followRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    val message = response.body()?.get("message")?.asString
                    // Display a message to the user indicating success
                    Toast.makeText(this@VisitProfile, message, Toast.LENGTH_SHORT).show()
                } else {
                    // Handle error response
                    // You can get the error message from response.errorBody() if needed
                    Toast.makeText(this@VisitProfile, "Failed to follow user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle network errors or other failures
                Toast.makeText(this@VisitProfile, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
