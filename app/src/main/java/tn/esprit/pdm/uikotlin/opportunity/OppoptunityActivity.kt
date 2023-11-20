package tn.esprit.pdm.uikotlin.opportunity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.OpportunityActivityBinding
import tn.esprit.pdm.models.request.Opportunite
import tn.esprit.pdm.utils.apiOpportunite
import java.util.Locale

class OppoptunityActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<Opportunite>()
    private lateinit var adapter: OpportuniteAdapter
    private lateinit var binding: OpportunityActivityBinding
    private lateinit var desc: Array<String>
    private lateinit var opportuniteAdapter: OpportuniteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OpportunityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        opportuniteAdapter = OpportuniteAdapter(emptyList())
        recyclerView.adapter = opportuniteAdapter

        val items = listOf("Grand Tunis", "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa",
            "Jendouba", "Kairouan", "Kasserine", "Kébili", "Le Kef", "Mahdia", "La Manouba",
            "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
            "Tataouine", "Tozeur", "Zaghouan")
        val lotfi = ArrayAdapter(this, R.layout.lieu, items)
        val items1 = listOf("temps plains", "temps partiel", "contrat", "travail temporaire")
        val lotfi1 = ArrayAdapter(this, R.layout.type_de_contrat, items1)

        binding.lotfiadd.setOnClickListener() {
            startActivity(Intent(this@OppoptunityActivity, Addopportunite::class.java))
        }

        desc = arrayOf(
            getString(R.string.architecte_applications_mobiles),
            getString(R.string.concepteur_experience_utilisateur_mobile),
            getString(R.string.developpeur_applications_android),
            getString(R.string.developpeur_applications_ios),
            getString(R.string.developpeur_applications_hybrides),
            getString(R.string.software_developer),
            getString(R.string.developpeur_applications_mobiles),
            getString(R.string.developpeur_interface_utilisateur_mobile)
        )

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = OpportuniteAdapter(mList)
        recyclerView.adapter = adapter
        fetchOpportunitesFromBackend()

        adapter.setOnItemClickListener(object : OpportuniteAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@OppoptunityActivity, DescriptionData::class.java)
                intent.putExtra("title", mList[position].title)
                intent.putExtra("description", desc[position])
                startActivity(intent)
            }
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        // Assurez-vous de décommenter cette ligne si vous prévoyez d'utiliser la fonction addDataToList
        // addDataToList()
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Opportunite>()
            for (i in mList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun fetchOpportunitesFromBackend() {
        val apiOpportunite = apiOpportunite.create()

        apiOpportunite.getAllOpportunites().enqueue(object : Callback<List<Opportunite>> {
            override fun onResponse(call: Call<List<Opportunite>>, response: Response<List<Opportunite>>) {
                if (response.isSuccessful) {
                    val nouvellesOpportunites = response.body()
                    runOnUiThread {
                        opportuniteAdapter.updateOpportunites(nouvellesOpportunites ?: emptyList())
                    }
                } else {
                    // Gérer l'erreur de réponse
                    Toast.makeText(this@OppoptunityActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Opportunite>>, t: Throwable) {
                // Gérer l'erreur d'échec
                Toast.makeText(this@OppoptunityActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
