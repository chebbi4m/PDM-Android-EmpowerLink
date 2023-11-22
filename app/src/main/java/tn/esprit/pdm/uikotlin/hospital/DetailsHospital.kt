package tn.esprit.pdm.uikotlin.hospital

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityDetailsHospitalBinding
import tn.esprit.pdm.utils.ReservationHospital
import tn.esprit.pdm.utils.apiHopital
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.models.Servicesoc

class DetailsHospital : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.button)
        val textView11 = findViewById<TextView>(R.id.textView11)
        val hopitalId = intent.getStringExtra("hopitalId") ?: ""

        val apiHopital = apiHopital.create()
        val call = apiHopital.getHopitalDetails(hopitalId)
        call.enqueue(object : Callback<Servicesoc> {
            override fun onResponse(call: Call<Servicesoc>, response: Response<Servicesoc>) {
                if (response.isSuccessful) {
                    val hopital = response.body()
                    // Mettez à jour votre interface utilisateur avec les détails de l'hôpital
                    updateUI(hopital)
                } else {
                    // Gérer l'erreur ici
                }
            }

            override fun onFailure(call: Call<Servicesoc>, t: Throwable) {
                // Gérer l'erreur ici
            }
        })

        button.setOnClickListener {
            val startReservationHospital = Intent(this@DetailsHospital, ReservationHospital::class.java)
            // Ajouter l'ID de l'hôpital à l'intent pour la prochaine activité
            startReservationHospital.putExtra("hopitalId", hopitalId)
            startActivity(startReservationHospital)
        }
    }

    private fun updateUI(hopital: Servicesoc?) {
        // Mettez à jour votre interface utilisateur avec les détails de l'hôpital
        if (hopital != null) {
            binding.textView11.text = hopital.description.toString()
            // Mettez à jour d'autres vues en fonction de vos besoins
        }
    }
}

