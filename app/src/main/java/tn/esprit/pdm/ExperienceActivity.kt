package tn.esprit.pdm

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.utils.RetrofitInstance

class ExperienceActivity : AppCompatActivity() {

    private val apiService = RetrofitInstance.createApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        val communityId = intent.getIntExtra("communityId", -1)

        // Fetch experiences using Retrofit
        lifecycleScope.launch {
            try {
                val communityExperiences = apiService.getExperiencesByCommunity(communityId.toLong())
                updateUI(communityExperiences)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ApiError", e.message ?: "Unknown error")
            }
        }

        val addButton: FloatingActionButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            // Show the AddExperienceFragment as a dialog
            val addExperienceFragment = AddExperienceFragment()
            addExperienceFragment.show(supportFragmentManager, "addExperienceDialog")
        }
    }

    private fun updateUI(communityExperiences: List<Experience>) {
        val experienceListView: ListView = findViewById(R.id.experienceListView)
        val experienceAdapter = ExperienceAdapter(this@ExperienceActivity, communityExperiences)
        experienceListView.adapter = experienceAdapter
    }
}
