package tn.esprit.pdm.uikotlin.opportunity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.OpportunityActivityBinding
import java.util.Locale


class OppoptunityActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<LanguageData>()
    private lateinit var adapter: LanguageAdapter
    private lateinit var binding: OpportunityActivityBinding
    lateinit var desc : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OpportunityActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val items = listOf("Grand Tunis", "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa",
            "Jendouba", "Kairouan", "Kasserine", "Kébili", "Le Kef", "Mahdia", "La Manouba",
            "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
            "Tataouine", "Tozeur", "Zaghouan")
        val lotfi = ArrayAdapter(this, R.layout.lieu, items)
        val items1 = listOf("temps plains", "temps partiel", "contrat", "travail temporaire")
        val lotfi1 = ArrayAdapter(this,R.layout.type_de_contrat, items1)






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

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = LanguageAdapter(mList)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : LanguageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

               //Toast.makeText(this@MainActivity, "clicked $position ", Toast.LENGTH_SHORT).show()
                val intent = Intent( this@OppoptunityActivity, DescriptionData::class.java)

                intent.putExtra("title",mList[position].title)
                intent.putExtra("description",desc[position])
                startActivity(intent)
            }


        }


        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<LanguageData>()
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

    private fun addDataToList() {
        mList.add(LanguageData("architecte_applications_mobilesa", R.drawable.architecte_applications_mobiles))
        mList.add(LanguageData("concepteur_experience_utilisateur_mobile", R.drawable.concepteur_experience_utilisateur_mobile))
        mList.add(LanguageData("developpeur_applications_android", R.drawable.developpeur_applications_android))
        mList.add(LanguageData("developpeur_applications_ios", R.drawable.developpeur_applications_ios))
        mList.add(LanguageData("developpeur_applications_hybrides", R.drawable.developpeur_applications_hybrides))
        mList.add(LanguageData("developpeur_applications_mobiles", R.drawable.developpeur_applications_mobiles))
        mList.add(LanguageData("developpeur_interface_utilisateur_mobile", R.drawable.developpeur_interface_utilisateur_mobile))
        mList.add(LanguageData("software_developer", R.drawable.software_developer))
    }


}