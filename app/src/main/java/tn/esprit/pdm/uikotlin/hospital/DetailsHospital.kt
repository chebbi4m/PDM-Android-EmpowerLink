package tn.esprit.pdm.uikotlin.hospital
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityDetailsHospitalBinding
import tn.esprit.pdm.models.Servicesoc
import tn.esprit.pdm.utils.apiHopital
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import tn.esprit.pdm.R


class DetailsHospital : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.button)
        val textView8 = findViewById<TextView>(R.id.textView8)
        val textView11 = findViewById<TextView>(R.id.textView11)
        val nom = intent.getStringExtra("nom")
        val hopitalName = intent.getStringExtra("hospital_name")

        if (nom != null) {
            val apiHopital = apiHopital.create()
            val call = apiHopital.getHopitalDetails(nom)
            call.enqueue(object : Callback<Servicesoc> {
                override fun onResponse(call: Call<Servicesoc>, response: Response<Servicesoc>) {
                    if (response.isSuccessful) {
                        val hopital = response.body()
                        // Mettre à jour l'UI avec les détails de l'hôpital
                        updateUI(hopital)
                    } else {
                        // Gérer les erreurs ici
                    }
                }

                override fun onFailure(call: Call<Servicesoc>, t: Throwable) {
                    // Gérer les erreurs ici
                }
            })
        } else if (hopitalName != null) {
            // Utiliser le nom de l'hôpital pour récupérer les détails
            val apiHopital = apiHopital.create()
            apiHopital.getHopitalDetails(hopitalName).enqueue(object : Callback<Servicesoc> {
                override fun onResponse(call: Call<Servicesoc>, response: Response<Servicesoc>) {
                    if (response.isSuccessful) {
                        val hopital = response.body()
                        // Mettre à jour l'UI avec les détails de l'hôpital
                        updateUI(hopital)
                    } else {
                        // Gérer les erreurs ici
                    }
                }

                override fun onFailure(call: Call<Servicesoc>, t: Throwable) {
                    // Gérer les erreurs ici
                }
            })
        }

        button.setOnClickListener {
            val startReservationHospital = Intent(this@DetailsHospital, ReservationHospital::class.java)
            // Ajouter l'ID de l'hôpital à l'intent pour l'activité suivante
            startReservationHospital.putExtra("nom", nom)
            startActivity(startReservationHospital)
        }
    }

    private fun updateUI(hopital: Servicesoc?) {
        if (hopital != null) {
            binding.textView8.text = hopital.nom ?: "No Name"
            binding.textView11.text = hopital.description ?: "No Description"
            // Mettre à jour les autres vues si nécessaire
        }
    }
}
