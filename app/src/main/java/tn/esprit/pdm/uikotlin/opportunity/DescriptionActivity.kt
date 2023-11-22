package tn.esprit.pdm.uikotlin.opportunity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityDescriptionBinding

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val description = intent.getStringExtra("description")
        val title = intent.getStringExtra("title")
        val lieu = intent.getStringExtra("lieu")
        // Utilisez la description pour mettre à jour votre TextView
        binding.titleTv.text = description
        binding.title.text=title
        binding.liew.text=lieu
        // Utilisez setImageResource() pour définir l'image statique dans l'ImageView
        binding.logoIv.setImageResource(R.drawable.software_developer)
    }
}