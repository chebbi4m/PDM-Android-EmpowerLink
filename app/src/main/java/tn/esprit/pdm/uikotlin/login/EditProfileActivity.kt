package tn.esprit.pdm.uikotlin.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.databinding.ActivityEditBinding
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.utils.Apiuser

class EditProfileActivity:AppCompatActivity() {

    private val apiuser = Apiuser.create()
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialiser la session
        sessionManager = SessionManager(this)
        binding.imageView3.setOnClickListener(){
        startActivity(Intent(this@EditProfileActivity,ProfileActivity::class.java))
        }
        binding.registerButton.setOnClickListener {
            editProfile()
        }
    }

    private fun editProfile() {
        val token = sessionManager.getUserId().toString()
        val decodedToken = sessionManager.decodeToken(token)

        Glide.with(this).load(decodedToken.image)
            .circleCrop()
            .override(170,170)
            .into(binding.logoMain)
        // Vérifiez si le décodage du jeton a réussi et obtenez l'ID de l'utilisateur
        val userId = decodedToken.userId

        if (userId.isNullOrEmpty()) {
            // Gérer le cas où l'ID de l'utilisateur n'est pas disponible
            Snackbar.make(binding.root, "User ID not available", Snackbar.LENGTH_SHORT).show()
            return
        }

        val editProfileRequest = LoginRequest(
            userId = userId,
            firstname = binding.firstName.text.toString(),
            lastname = binding.lastName.text.toString(),
            email = binding.email.text.toString(),
            adress = binding.adress.text.toString(),
            number = binding.phonenumber.text.toString(),
            description = binding.description.text.toString()
        )

        // Appeler la méthode d'inscription de l'API
        apiuser.editprofile(editProfileRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Traitement réussi, redirigez l'utilisateur vers l'activité d'accueil par exemple
                    startActivity(Intent(this@EditProfileActivity, ProfileActivity::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
                } else {
                    // Afficher une erreur en cas de réponse non réussie
                    Snackbar.make(binding.root, "Error: ${response.code()}", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Gérer les erreurs liées à la connexion, etc.
                Log.e("EditProfileActivity", "Error: ${t.message}")
                Snackbar.make(binding.root, "Error: ${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}