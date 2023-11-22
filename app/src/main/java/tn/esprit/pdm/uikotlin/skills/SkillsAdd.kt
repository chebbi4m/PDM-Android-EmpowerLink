package tn.esprit.pdm.uikotlin.skills

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityAddSkillsBinding
import tn.esprit.pdm.utils.Apiuser
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.login.LoginActivity

class SkillsAdd : AppCompatActivity() {

    private lateinit var binding: ActivityAddSkillsBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSkillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this) // Initialize sessionManager here

        binding.addskills.setOnClickListener {
            addSkills()
        }
    }

    private fun addSkills() {
        // Rest of your code remains unchanged
        val token = sessionManager.getUserId().toString()
        val decodedToken = sessionManager.decodeToken(token)
        val apiuser = Apiuser.create()

        // Assuming you have the userId and skills data
        val UserId = decodedToken.userId

        // val skills = binding.tiSkills.text.toString()

        // Validate skills if needed

        val addSkillsRequest = LoginRequest(
            userId = UserId,
            skills = listOf(binding.tiSkills.text.toString())
        )

        val call = apiuser.addSkills(addSkillsRequest)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    val jsonObject = response.body()
                    startActivity(Intent(this@SkillsAdd, ProfileActivity::class.java))
                    // Process jsonObject as needed
                } else {
                    // Handle error response
                    val errorBody = response.errorBody()?.string()
                    // Process errorBody as needed
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle network error
                t.printStackTrace()
            }
        })
    }
}
