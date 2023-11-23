package tn.esprit.pdm.uikotlin.hospital

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityDetailsHospitalBinding
import tn.esprit.pdm.models.Servicesoc
import tn.esprit.pdm.utils.apiHopital
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.utils.ReservationHospital

class DetailsHospital : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.button)
        val textView8 = findViewById<TextView>(R.id.textView8)
        val textView11 = findViewById<TextView>(R.id.textView11)
        val hopitalId = "655ba12b467819b918c22f6b"

        val apiHopital = apiHopital.create()
        val call = apiHopital.getHopitalDetails(hopitalId)
        call.enqueue(object : Callback<Servicesoc> {
            override fun onResponse(call: Call<Servicesoc>, response: Response<Servicesoc>) {
                if (response.isSuccessful) {
                    val hopital = response.body()
                    // Update your UI with hospital details
                    updateUI(hopital)
                } else {
                    // Handle error here
                }
            }

            override fun onFailure(call: Call<Servicesoc>, t: Throwable) {
                // Handle error here
            }
        })

        button.setOnClickListener {
            val startReservationHospital = Intent(this@DetailsHospital, ReservationHospital::class.java)
            // Add hospital ID to the intent for the next activity
            startReservationHospital.putExtra("hopitalId", hopitalId)
            startActivity(startReservationHospital)
        }
    }

    private fun updateUI(hopital: Servicesoc?) {
        if (hopital != null) {
            binding.textView8.text = hopital.nom ?: "No Name"
            binding.textView11.text = hopital.description ?: "No Description"
            // Update other views as needed
        }
    }
}
