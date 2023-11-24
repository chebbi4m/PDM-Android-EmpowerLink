
package tn.esprit.pdm.uikotlin.experience

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityExperienceBinding

class CommunityExperienceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExperienceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExperienceBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
