package tn.esprit.pdm.uikotlin.searchuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileVisitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiuser = Apiuser.create()

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

                            // Configure your views to display the user details
                            binding.tvFullname.text = fullname
                            binding.tvSpeciality.text = speciality
                            binding.tvAddress.text = address
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

}
