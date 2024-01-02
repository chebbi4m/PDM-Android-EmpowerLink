package tn.esprit.pdm.uikotlin.opportunity

import tn.esprit.pdm.MainActivity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import tn.esprit.pdm.databinding.AddopportuniteBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.models.request.Opportunite
import tn.esprit.pdm.utils.apiOpportunite



class Addopportunite :AppCompatActivity(){
    private val ApiOpportunite = apiOpportunite.create()
    private lateinit var binding: AddopportuniteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddopportuniteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ajoutopportuniteButton.setOnClickListener(){
            editopportunite()
        }



    }
    private fun editopportunite() {






        val addopprtunite = Opportunite(
            title = binding.opportuniteTitleEditText.text.toString(),
            description = binding.opportuniteDescriptionEditText.text.toString(),
            skill = binding.opportuniteSkillEditText.text.toString(),
            contactEmail = binding.opportuniteContactEmailEditText.text.toString(),
            salary = binding.opportuniteSalaryEditText.text.toString(),
            nomEntreprise = binding.opportuniteNomEntrepriseEditText.text.toString(),
            lieu = binding.opportuniteLieuEditText.text.toString(),
            Typedecontrat = binding.opportuniteTypeDeContratEditText.text.toString()
        )

        // Appeler la méthode d'inscription de l'API
        ApiOpportunite.addopp(addopprtunite).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Traitement réussi, redirigez l'utilisateur vers l'activité d'accueil par exemple
                    startActivity(Intent(this@Addopportunite, OppoptunityActivity::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
                } else {
                    // Afficher une erreur en cas de réponse non réussie
                    Snackbar.make(binding.root, "Error: ${response.code()}", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Gérer les erreurs liées à la connexion, etc.
                Log.e("EditProfileActivity", "Error: ${t.message}")
                Snackbar.make(binding.root, "Error: ${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })
    }


}
