package tn.esprit.pdm.uikotlin.experience

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.utils.RetrofitInstance
import tn.esprit.pdm.utils.RetrofitInstance.experienceService

class ExperienceActivity : AppCompatActivity() {

    private val apiService = RetrofitInstance.createApiService()
    private lateinit var communityExperiences: MutableList<Experience>
    private lateinit var experienceAdapter: NewExperienceAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var inputLayout: TextInputLayout
    private lateinit var inputEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        val communityId = intent.getIntExtra("communityId", -1)
        communityExperiences = mutableListOf()

        recyclerView = findViewById(R.id.experienceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        inputLayout = findViewById(R.id.inputLayout)
        inputEditText = findViewById(R.id.experienceSentText)

        val addButton: ImageButton = findViewById(R.id.sendButton)
        addButton.setOnClickListener {
            val inputText = inputEditText.text.toString()
            if (inputText.isNotEmpty()) {
                val username = "wassim"
                val experience = Experience(username = username, experienceText = inputText, communityId = communityId.toString())
                addExperience(experience)

                // Clear the input field
                inputEditText.text = null

                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            val response = experienceService.createExperience(experience)
                            if (response.isSuccessful) {

                            } else {

                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            // Handle network error
                        }
                    }
                }
            }
        }

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
    }

    private fun updateUI(communityExperiences: List<Experience>) {
        val onItemClick: (Experience) -> Unit = { clickedExperience ->
            Log.d("ExperienceClicked", "Clicked on experience: ${clickedExperience.experienceTitle}")
        }

        experienceAdapter = NewExperienceAdapter(this@ExperienceActivity, communityExperiences, onItemClick)
        recyclerView.adapter = experienceAdapter
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(communityExperiences.size - 1)
    }

    private fun onExperienceAddedSuccessfully(experience: Experience, communityId: String) {
        addExperience(experience)

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
        communityExperiences.add(experience)
        recyclerView.adapter?.notifyDataSetChanged()
        scrollToBottom()
    }
}