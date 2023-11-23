package tn.esprit.pdm.uikotlin.experience

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import tn.esprit.pdm.R
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
        val experienceRecyclerView: RecyclerView = findViewById(R.id.homePageRecyclerView)

        // Set up RecyclerView with the ExperienceAdapter
        val experienceAdapter = ExperienceAdapter(this@ExperienceActivity, communityExperiences) { clickedExperience ->
            // Handle item click as needed
            // For example, you can navigate to a detailed view
            Log.d("ExperienceClicked", "Clicked on experience: ${clickedExperience.experienceTitle}")
        }

        experienceRecyclerView.adapter = experienceAdapter
        experienceRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
