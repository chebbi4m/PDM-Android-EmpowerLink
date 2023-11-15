package tn.esprit.pdm.uikotlin.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityEditBinding
import tn.esprit.pdm.databinding.LoginBinding
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.utils.Apiuser

class EditProfileActivity:AppCompatActivity() {

    val apiuser = Apiuser.create()
    private lateinit var sessionManager: SessionManager
    private lateinit var binding:ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}