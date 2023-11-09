package tn.esprit.pdm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// CommunityExperiencesActivity.kt
class CommunityExperiencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve community ID from intent or other sources
        val communityId = intent.getIntExtra("communityId", -1)

        // Open the ExperienceActivity and pass the community ID
        val intent = Intent(this, ExperienceActivity::class.java).apply {
            putExtra("communityId", communityId)
        }
        startActivity(intent)
        finish()  // Optional: Finish the current activity
    }
}
