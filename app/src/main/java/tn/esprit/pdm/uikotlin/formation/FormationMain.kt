package tn.esprit.pdm.uikotlin.formation

import FormationAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityFormationMainBinding
import tn.esprit.pdm.models.Education
import tn.esprit.pdm.models.Formation
import tn.esprit.pdm.uikotlin.educations.EducationAdapter
import tn.esprit.pdm.utils.ApiFormationEducation
class FormationMain : AppCompatActivity() {
    private lateinit var apiFormation: ApiFormationEducation
    private lateinit var formationList: RecyclerView
    private lateinit var educationList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formation_main)

        // Set up RecyclerView
        val adapter = FormationAdapter(emptyList()) // Pass an empty list initially
        formationList = findViewById(R.id.formationList)
        formationList.adapter = adapter
        formationList.layoutManager = LinearLayoutManager(this)
        // Set up RecyclerView for educations
        val educationAdapter = EducationAdapter(emptyList())
        educationList = findViewById(R.id.educationList)
        educationList.adapter = educationAdapter
        educationList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // Initialize Retrofit service
        apiFormation = ApiFormationEducation.create()

        fetchEducation()
        fetchFormations()
    }

    private fun fetchFormations() {
        Log.e("bbb", "bbb")
        val call = apiFormation.getAllformations()

        call.enqueue(object :
            Callback<MutableList<Formation>> {

            override fun onResponse(call: Call<MutableList<Formation>>, response:
            Response<MutableList<Formation>>
            ) {
                if (response.isSuccessful) {
                    val formations = response.body()
                    Log.e("hhh", "hhh")
                    Log.e("test", formations.toString())
                    if (formations != null) {
                        updateFormationList(formations)
                    }
                } else {
                    Log.e("hhh", "hhh")
                }
            }

            override fun onFailure(call: Call<MutableList<Formation>>, t: Throwable) {
                Log.e("zzz", "zz")
            }
        })
    }
    private fun fetchEducation() {
        Log.e("bbb", "bbb")
        val call = apiFormation.getAllEducations()

        call.enqueue(object :
            Callback<MutableList<Education>> {

            override fun onResponse(call: Call<MutableList<Education>>, response:
            Response<MutableList<Education>>
            ) {
                if (response.isSuccessful) {
                    val educations = response.body()
                    Log.e("hhh", "hhh")
                    Log.e("test", educations.toString())
                    if (educations != null) {
                        updateEducationList(educations)
                    }
                } else {
                    Log.e("hhh", "hhh")
                }
            }

            override fun onFailure(call: Call<MutableList<Education>>, t: Throwable) {
                Log.e("zzz", "zz")
            }
        })
    }
    private fun updateEducationList(educations: MutableList<Education>) {
        val adapter = educationList.adapter as EducationAdapter
        adapter.setData(educations)
    }
    private fun updateFormationList(formations: MutableList<Formation>) {
        val adapter = formationList.adapter as FormationAdapter
        adapter.setData(formations)
    }
}

