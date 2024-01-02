package tn.esprit.pdm.uikotlin.hospital
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.databinding.ActivityDetailsHospitalBinding
import tn.esprit.pdm.models.Servicesoc
import tn.esprit.pdm.utils.apiHopital
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import tn.esprit.pdm.R
import tn.esprit.pdm.models.Soins


class DetailsHospital : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsHospitalBinding
    private val soinsList = ArrayList<Soins>()
    private lateinit var soinsAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.button)
        val textView8 = findViewById<TextView>(R.id.textView8)
        val textView11 = findViewById<TextView>(R.id.textView11)
        val hopitalId = intent.getStringExtra("hopitalId")
        val hopitalName = intent.getStringExtra("hospital_name")
        val listViewServices = findViewById<ListView>(R.id.listViewServices)
        soinsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        listViewServices.adapter = soinsAdapter
        fetchSoins()

        if (hopitalId != null) {
            val apiHopital = apiHopital.create()
            val call = apiHopital.getHopitalDetails(hopitalId)
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
            val startReservationHospital =
                Intent(this@DetailsHospital, ReservationHospital::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) }
            // Ajouter l'ID de l'hôpital à l'intent pour l'activité suivante
            startReservationHospital.putExtra("hopitalId", hopitalId)
            startActivity(startReservationHospital)
        }
        val edittextSource = findViewById<EditText>(R.id.source)
        val edittextDestination = findViewById<EditText>(R.id.destination)
        val Button = findViewById<Button>(R.id.btnSubmit)

        Button.setOnClickListener {
            val source = edittextSource.text.toString()
            val destination = edittextDestination.text.toString()

            if (source.isEmpty() && destination.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Enter both source and destination",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val uri = Uri.parse("https://www.google.com/maps/dir/$source/$destination")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.`package` = "com.google.android.apps.maps"
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

    }



    private fun updateUI(hopital: Servicesoc?) {
        if (hopital != null) {
            binding.textView8.text = hopital.nom ?: "No Name"
            binding.textView11.text = hopital.description ?: "No Description"
            // Mettre à jour les autres vues si nécessaire
        }
    }
    private fun fetchSoins() {
        val apiService = apiHopital.create()
        apiService.getSoins().enqueue(object : Callback<List<Soins>> {
            override fun onResponse(call: Call<List<Soins>>, response: Response<List<Soins>>) {
                if (response.isSuccessful) {
                    val soins = response.body()
                    if (soins != null) {
                        // Clear existing list and add new data
                        soinsList.clear()
                        soinsList.addAll(soins)
                        // Extract the names and notify the adapter
                        val soinsNames = soins.map { it.nom.orEmpty() }
                        soinsAdapter.clear()
                        soinsAdapter.addAll(soinsNames)
                        soinsAdapter.notifyDataSetChanged()
                    } else {
                        // Handle errors here
                    }
                } else {
                    // Handle errors here
                }
            }

            override fun onFailure(call: Call<List<Soins>>, t: Throwable) {
                // Handle errors here
            }
        })
    }

    private fun displaySoins(soins: List<Soins>) {
        // Display the list of soins
    }


}