package tn.esprit.pdm.uikotlin.opportunity


import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityDescriptionBinding

import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.models.request.Opportunite
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.formation.Paiement
import tn.esprit.pdm.utils.ApiFormationEducation
import tn.esprit.pdm.utils.apiOpportunite

class DescriptionActivity : AppCompatActivity() {


    private val listeOpportunites: MutableList<Opportunite> = mutableListOf()
    private val CHOOSE_PDF_FROM_DEVICE = 1001
    private lateinit var chooseFileBtn: FloatingActionButton
    private lateinit var pathTv: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityDescriptionBinding
    private val ApiOpportunite = apiOpportunite.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val opportunityId = intent.getStringExtra("opportunityId")

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        chooseFileBtn = findViewById(R.id.choose_file_btn)
        pathTv = findViewById(R.id.path_tv)

        chooseFileBtn.setOnClickListener {
            // Gérer l'événement de clic du bouton chooseFileBtn ici
        }

        chooseFileBtn.setOnClickListener {
            callChooseFileFromDevice()
        }



        binding.imageView3.setOnClickListener {
            startActivity(Intent(this@DescriptionActivity, OppoptunityActivity::class.java))
        }

        val description = intent.getStringExtra("description")
        val title = intent.getStringExtra("title")
        val lieu = intent.getStringExtra("lieu")
        val skill = intent.getStringExtra("skill")
        val email= intent.getStringExtra("email")
        val entreprise = intent.getStringExtra("enterprise")
        val salary = intent.getStringExtra("salary")
        val contract = intent.getStringExtra("contract")

        // Utilize the description to update your TextView
      // val opportunityId = intent.getStringExtra("opportunityId")

        binding.titleTv.text = description
        binding.title.text = title
        binding.liew.text = lieu
        binding.skillText.text = skill
        binding.emailText.text = email
        binding.enterpriseText.text = entreprise
        binding.salaryText.text = salary
        binding.contractText.text = contract

        // Utilize setImageResource() to set the static image in the ImageView
        binding.logoIv.setImageResource(R.drawable.software_developer)

        // Example: Triggering the API call when a button is clicked
        binding.postulerButton.setOnClickListener {
            // Replace 'your_opportunity_id' and 'your_username' with the actual data
            // Make the API call
            applyToOpportunity()
        }
        for (opportunite in listeOpportunites) {
            val imageView: ImageView = findViewById(R.id.logoIv)
            Picasso.get().load(opportunite.imageUrl).into(imageView)
        }
    }

    private fun callChooseFileFromDevice() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"

        // Ajoutez ici d'autres options ou paramètres à l'intent, si nécessaire

        // Lancez l'activité pour sélectionner un fichier
        startActivityForResult(intent, CHOOSE_PDF_FROM_DEVICE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == CHOOSE_PDF_FROM_DEVICE && resultCode == RESULT_OK) {
            if (resultData != null) {
                Log.d(TAG, "onActivityResult: " + resultData.data)
                pathTv.text = "File Path: " + resultData.data
            }
        }
    }


    private fun applyToOpportunity() {
        val token = sessionManager.getUserId().toString()
        val decodedToken = sessionManager.decodeToken(token)

        val userId = decodedToken.userId//decodedToken.userId
        val opportunityId ="655e01f45283593e56893580"
        val info = apiOpportunite.AddParticipantRequest(
            opportunityId = opportunityId,//intent.getStringExtra("_id").toString(),
            userId = userId.toString(),
        )
        val call = ApiOpportunite.addParticipant(info)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (opportunityId != null) {

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
