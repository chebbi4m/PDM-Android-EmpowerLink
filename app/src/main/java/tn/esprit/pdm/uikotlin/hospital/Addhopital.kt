package tn.esprit.pdm.uikotlin.hospital

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import tn.esprit.pdm.utils.apiHopital
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.MainActivity
import tn.esprit.pdm.databinding.AddhopitalBinding
import tn.esprit.pdm.models.Servicesoc
import tn.esprit.pdm.uikotlin.serviceSocial.SocialServiceActivity


class Addhopital :AppCompatActivity(){
    private val ApiHopital =apiHopital.create()
    private lateinit var binding: AddhopitalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = AddhopitalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.add.setOnClickListener(){
            addhopital()
        }

    }
    private fun addhopital() {



        val addhopital= Servicesoc(
            description = binding.tiFullName.text.toString(),
            nom=binding.tiEmail.text.toString()
        )

        // Appeler la méthode d'inscription de l'API
        ApiHopital.addhop(addhopital).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Traitement réussi, redirigez l'utilisateur vers l'activité d'accueil par exemple
                    startActivity(Intent(this@Addhopital, SocialServiceActivity::class.java))
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