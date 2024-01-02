package tn.esprit.pdm.uikotlin.opportunity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    private var currentQuery: String? = null
    private var currentLieu: String? = null
    private var currentTypeContrat: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: OpportunityActivityBinding
    private lateinit var opportuniteAdapter: OpportuniteAdapter
    private lateinit var desc: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OpportunityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        opportuniteAdapter = OpportuniteAdapter(emptyList())
        recyclerView.adapter = opportuniteAdapter

        val items = listOf("TUNIS", "ARIANA", "BENAROUS", "SFAX", "SOUSSE")
        val items1 = listOf("CDD", "CDI", "Stage")

        val lotfi = ArrayAdapter(this, R.layout.lieu, items)
        val lotfi1 = ArrayAdapter(this, R.layout.type_de_contrat, items1)

        fetchOpportunitesFromBackend()

        binding.lotfiadd.setOnClickListener {
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

        binding.autoCompleteTextView.setAdapter(lotfi)
        binding.autoCompleteTextView2.setAdapter(lotfi1)

        opportuniteAdapter.setOnItemClickListener(object : OpportuniteAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@OppoptunityActivity, DescriptionData::class.java)
                intent.putExtra("description", desc[position])
                startActivity(intent)
            }
        })

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            currentLieu = items[position]
            filterOpportunites()
        }

        binding.autoCompleteTextView2.setOnItemClickListener { _, _, position, _ ->
            currentTypeContrat = items1[position]
            filterOpportunites()
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
                    Log.e("OppoptunityActivity", "Error: ${response.code()}")
                    Toast.makeText(this@OppoptunityActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Opportunite>>, t: Throwable) {
                Log.e("OppoptunityActivity", "Error: ${t.message}")
                Toast.makeText(this@OppoptunityActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterList(query: String?) {
        currentQuery = query

        val filteredList = ArrayList<Opportunite>()
        val newList = opportuniteAdapter.getOpportunites()

        newList.filterTo(filteredList) { opportunite ->
            val matchQuery = query.isNullOrEmpty() || opportunite.description.toString().contains(
                query,
                ignoreCase = true
            )

            val matchLieu =
                currentLieu.isNullOrEmpty() || opportunite.lieu.equals(currentLieu, ignoreCase = true)

            val matchTypeContrat =
                currentTypeContrat.isNullOrEmpty() || opportunite.Typedecontrat.equals(
                    currentTypeContrat,
                    ignoreCase = true
                )

            matchQuery && matchLieu && matchTypeContrat
        }

        if (query == "ARIANA") {
            binding.autoCompleteTextView.setText(query)
        }

        opportuniteAdapter.updateOpportunites(filteredList)
    }

    private fun filterOpportunites() {
        val filteredList = opportuniteAdapter.getOpportunites().filter { opportunite ->
            val matchLieu =
                currentLieu.isNullOrEmpty() || opportunite.lieu.equals(currentLieu, ignoreCase = true)
            val matchTypeContrat =
                currentTypeContrat.isNullOrEmpty() || opportunite.Typedecontrat.equals(
                    currentTypeContrat,
                    ignoreCase = true
                )

            matchLieu && matchTypeContrat
        }

        opportuniteAdapter.updateOpportunites(filteredList)
    }
}
