package tn.esprit.pdm.uikotlin.formation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Education
import tn.esprit.pdm.models.Formation
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.utils.ApiFormationEducation

class DetailsFormationsActivity : AppCompatActivity() {
    private lateinit var apiFormation: ApiFormationEducation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_formations)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set custom centered title with black text color
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title
        val customTitle = findViewById<TextView>(R.id.custom_title)
        customTitle.text = "Details"
        customTitle.setTextColor(getColor(android.R.color.black)) // Set text color
        apiFormation = ApiFormationEducation.create()

        val Img = findViewById<ImageView>(R.id.ImgFormation)
        val TitleFormation = findViewById<TextView>(R.id.TitleFormation)
        val DescriptionFormation = findViewById<TextView>(R.id.DescriptionFormation)
        val nbParticipantText = findViewById<TextView>(R.id.nbParticipantText)
        val RegisterButton = findViewById<Button>(R.id.RegisterButton)


        // Retrieve data from the Intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val nbParticipant = intent.getIntExtra("nbParticipant", 0)
        val nbPlace = intent.getIntExtra("nbPlace", 0)
        val imgFormation = intent.getStringExtra("image")
        Log.d("nbPlace", nbPlace.toString())
        Glide.with(this).load(imgFormation).into(Img)
        TitleFormation.text = title
        DescriptionFormation.text = description
        nbParticipantText.text =  "Number of Participants: ${nbParticipant}"
        // Check if nbParticipant is equal to nbPlace

        if (nbParticipant == nbPlace) {
            // If they are equal, make the RegisterButton unclickable and change its text
            RegisterButton.isClickable = false
            RegisterButton.text = "Complete"
        }
        Log.d("aaa", intent.getStringExtra("_id").toString())
        RegisterButton.setOnClickListener {
            if (nbParticipant == nbPlace) {
                AlertDialog.Builder(this@DetailsFormationsActivity)
                    .setTitle("Sorry")
                    .setMessage("You cannot register in this formations")
                    .setPositiveButton("OK") { dialog, _ ->
                        // Proceed with the intent when the user clicks "OK"
                        dialog.dismiss()
                    }
                    .show()
            }
            else{
                val info = ApiFormationEducation.AddParticipantRequest(
                    formationId = intent.getStringExtra("_id").toString(),
                    userId = "6550bce0a0d1a744ea94d641"
                )
                val call = apiFormation.addParticipant(info)
                call.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if (response.isSuccessful) {
                            /*// Show AlertDialog on success
                            AlertDialog.Builder(this@DetailsFormationsActivity)
                                .setTitle("Success")
                                .setMessage("Participant added successfully")
                                .setPositiveButton("OK") { dialog, _ ->
                                    // Proceed with the intent when the user clicks "OK"
                                    val intent = Intent(this@DetailsFormationsActivity, FormationMain::class.java)
                                    startActivity(intent)
                                    finish()
                                    dialog.dismiss()
                                }
                                .show()*/
                            val intent = Intent(this@DetailsFormationsActivity, Paiement::class.java)



                            // Start the activity
                            startActivity(intent)
                        } else {
                            // Show AlertDialog on non-success response
                            AlertDialog.Builder(this@DetailsFormationsActivity)
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
                        AlertDialog.Builder(this@DetailsFormationsActivity)
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

    }
    // Handle back button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}