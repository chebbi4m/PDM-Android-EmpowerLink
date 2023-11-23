package tn.esprit.pdm.uikotlin.serviceSocial

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Servicesoc
import tn.esprit.pdm.utils.apiHopital
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.uikotlin.hospital.DetailsHospital


class SocialServiceActivity : AppCompatActivity() {


    private val ApiHopital = apiHopital.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_service)
        val listView: ListView = findViewById(R.id.HospitalListView)

        ApiHopital.getHopitaux().enqueue(object : Callback<List<Servicesoc>> {
            override fun onResponse(
                call: Call<List<Servicesoc>>,
                response: Response<List<Servicesoc>>
            ) {
                if (response.isSuccessful) {
                    val hopitauxList: List<Servicesoc>? = response.body()
                    if (!hopitauxList.isNullOrEmpty()) {
                        val listItems = hopitauxList.map { it.nom.orEmpty() }.toTypedArray()
                        val listAdapter =
                            ArrayAdapter(this@SocialServiceActivity, android.R.layout.simple_list_item_1, listItems)
                        listView.adapter = listAdapter

                        listView.setOnItemClickListener { parent, view, position, id ->
                            val selectedItem = parent.getItemAtPosition(position) as String
                            Toast.makeText(
                                this@SocialServiceActivity, "You have clicked on: $selectedItem",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Naviguer vers la page de détails avec le nom de l'hôpital en tant qu'extra
                            val intent = Intent(this@SocialServiceActivity, DetailsHospital::class.java)
                            intent.putExtra("hospital_name", selectedItem)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@SocialServiceActivity,
                            "La liste d'hôpitaux est vide",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SocialServiceActivity,
                        "Erreur: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Servicesoc>>, t: Throwable) {
                Toast.makeText(
                    this@SocialServiceActivity,
                    "Erreur: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}
