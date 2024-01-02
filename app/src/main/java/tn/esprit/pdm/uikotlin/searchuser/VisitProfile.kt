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
import tn.esprit.pdm.uikotlin.SessionManager


class VisitProfile : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityProfileVisitBinding
    private lateinit var apiuser: Apiuser
    private lateinit var visitedUserId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileVisitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
binding.tvFullname.setOnClickListener(){

}
        binding.imageView3.setOnClickListener(){
        startActivity(Intent(this@VisitProfile,SearchUsersActivity::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
        binding.follow.setOnClickListener(){
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

        val token = sessionManager.getUserImage().toString()
        val decodedToken = sessionManager.decodeToken(token)
        val userId = decodedToken.userId
        val targetUsername = getUsernameFromSharedPreferences()
        // Créer une instance de la demande de suivi
        val followRequest = LoginRequest(userId = userId, username = targetUsername)

        // Appeler la méthode followUser de l'API
        apiuser.followUser(followRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Le suivi a réussi
                    Toast.makeText(
                        this@VisitProfile,
                        "User followed successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Gérer les erreurs de réponse
                    Toast.makeText(
                        this@VisitProfile,
                        "Failed to follow user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Gérer les erreurs de requête
                Toast.makeText(
                    this@VisitProfile,
                    "Failed to follow user",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Utilisez la fonction followUser dans votre code où vous souhaitez appeler la fonction de suivi
// Par exemple, lorsque l'utilisateur appuie sur un bouton de suivi
// Assurez-vous de définir les valeurs correctes pour userId et targetUserId





}
