package tn.esprit.pdm.uikotlin.experience

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.utils.RetrofitInstance

class ExperienceActivity : AppCompatActivity() {

    private val apiService = RetrofitInstance.createApiService()
    private lateinit var communityExperiences: MutableList<Experience>
    private lateinit var experienceAdapter: ExperienceAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        val communityId = intent.getIntExtra("communityId", -1)
        communityExperiences = mutableListOf()

        recyclerView = findViewById(R.id.experienceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val fetchedExperiences = apiService.getExperiencesByCommunity(communityId.toLong())
                communityExperiences.addAll(fetchedExperiences)
                runOnUiThread {
                    updateUI(communityExperiences)
                    scrollToBottom()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ApiError", e.message ?: "Unknown error")
            }
        }

        val addButton: FloatingActionButton = findViewById(R.id.addButton)
        // Inside addButton.setOnClickListener
        addButton.setOnClickListener {
            // Show the AddExperienceFragment as a dialog
            val addExperienceFragment = AddExperienceFragment(
                communityId.toString(),
                { experience -> onExperienceAddedSuccessfully(experience, communityId.toString()) }
            )
            addExperienceFragment.show(supportFragmentManager, "addExperienceDialog")
        }

    }

    private fun updateUI(communityExperiences: List<Experience>) {
        // Define the item click callback
        val onItemClick: (Experience) -> Unit = { clickedExperience ->
            // Handle item click as needed
            Log.d("ExperienceClicked", "Clicked on experience: ${clickedExperience.experienceTitle}")
        }

        experienceAdapter = ExperienceAdapter(this@ExperienceActivity, communityExperiences, onItemClick)
        recyclerView.adapter = experienceAdapter
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(communityExperiences.size - 1)
    }

    private fun onExperienceAddedSuccessfully(experience: Experience,communityId :String) {
        addExperience(experience)

        // Fetch all experiences again and update the UI
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val fetchedExperiences = apiService.getExperiencesByCommunity(communityId.toLong())
                communityExperiences.clear()
                communityExperiences.addAll(fetchedExperiences)
                experienceAdapter.notifyDataSetChanged()
                scrollToBottom()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ApiError", e.message ?: "Unknown error")
            }
        }
    }


    private fun addExperience(experience: Experience) {
        // Add the new experience to the end of your dataset
        communityExperiences.add(experience)

        // Notify the adapter that the dataset has changed
        recyclerView.adapter?.notifyItemInserted(communityExperiences.size - 1)

        // Scroll to the newly added item
        scrollToBottom()
    }
}