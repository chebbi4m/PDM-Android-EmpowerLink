package tn.esprit.pdm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.databinding.ActivityProfilBinding

class ProfileActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)
    }
}