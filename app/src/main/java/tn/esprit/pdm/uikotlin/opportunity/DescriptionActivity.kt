package tn.esprit.pdm.uikotlin.opportunity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityDescriptionBinding

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.models.request.Opportunite
import tn.esprit.pdm.uikotlin.formation.Paiement
import tn.esprit.pdm.utils.ApiFormationEducation
import tn.esprit.pdm.utils.apiOpportunite

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private val ApiOpportunite = apiOpportunite.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView3.setOnClickListener {
            startActivity(Intent(this@DescriptionActivity, OppoptunityActivity::class.java))
        }

        val description = intent.getStringExtra("description")
        val title = intent.getStringExtra("title")
        val lieu = intent.getStringExtra("lieu")

        // Utilize the description to update your TextView
        binding.titleTv.text = description
        binding.title.text = title
        binding.liew.text = lieu

        // Utilize setImageResource() to set the static image in the ImageView
        binding.logoIv.setImageResource(R.drawable.software_developer)

        // Example: Triggering the API call when a button is clicked
        binding.postulerButton.setOnClickListener {
            // Replace 'your_opportunity_id' and 'your_username' with the actual data
            // Make the API call
            applyToOpportunity()
        }
    }

    private fun applyToOpportunity() {
        val userId = "655543ff30e2fe5090feb677"//decodedToken.userId
        val opportunityId ="655e01f45283593e56893580"
        val info = apiOpportunite.AddParticipantRequest(
            opportunityId = opportunityId,//intent.getStringExtra("_id").toString(),
            userId = userId,
        )
        val call = ApiOpportunite.addParticipant(info)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {

                    val intent = Intent(this@DescriptionActivity, ProfileActivity::class.java)



                    // Start the activity
                    startActivity(intent)
                } else {
                    // Show AlertDialog on non-success response
                    AlertDialog.Builder(this@DescriptionActivity)
                        .setTitle("Error")
                        .setMessage("Internal Server Error")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Show AlertDialog on failure
                AlertDialog.Builder(this@DescriptionActivity)
                    .setTitle("Error")
                    .setMessage("Failed to add participant: ${t.message}")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        })
    }


}
