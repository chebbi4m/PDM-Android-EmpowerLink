package tn.esprit.pdm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityProfilBinding
import tn.esprit.pdm.uikotlin.SessionManager

class HomeActivity :AppCompatActivity(){
private lateinit var sessionManager: SessionManager
private lateinit var binding : ActivityProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}