package tn.esprit.pdm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.databinding.ActivityProfilBinding
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.login.EditProfileActivity
import tn.esprit.pdm.uikotlin.login.ForgetPasswordActivity

class ProfileActivity: AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)
        binding.tiedit.setOnClickListener(){
            startActivity(Intent(this, EditProfileActivity::class.java))
        }



        sessionManager = SessionManager(this)
        val token = sessionManager.getUserEmail().toString()

        // Décodez le token
        val decodedToken = sessionManager.decodeToken(token)

        // Utilisez les informations du token
        binding.tiusername.text = decodedToken.email
        binding
    }

}