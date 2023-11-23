package tn.esprit.pdm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import tn.esprit.pdm.databinding.ActivityHomePageBinding
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.community.CommunityActivity
import tn.esprit.pdm.uikotlin.experience.ExperienceActivity
import tn.esprit.pdm.uikotlin.experience.ExperienceAdapter
import tn.esprit.pdm.uikotlin.formation.FormationMain
import tn.esprit.pdm.uikotlin.hospital.Addhopital
import tn.esprit.pdm.uikotlin.login.LoginActivity
import tn.esprit.pdm.uikotlin.opportunity.OppoptunityActivity
import tn.esprit.pdm.uikotlin.searchuser.SearchUsersActivity
import java.io.BufferedReader
import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.models.Education
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.educations.EducationAdapter
import tn.esprit.pdm.uikotlin.searchuser.SuggestionAdapter
import tn.esprit.pdm.utils.ApiFormationEducation
import tn.esprit.pdm.utils.Apiuser
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.google.gson.JsonObject
import tn.esprit.pdm.models.username

class HomePageActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var communityRecyclerView: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var apiFormation: ApiFormationEducation
    private lateinit var educationList: RecyclerView
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var suggestionList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        sessionManager = SessionManager(this)
        val token = sessionManager.getUserName().toString()

        // Decode the token
        val decodedToken = sessionManager.decodeToken(token)

        communityRecyclerView = findViewById(R.id.homePageRecyclerView)
        communityRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        suggestionAdapter = SuggestionAdapter(emptyList())
        suggestionList = findViewById(R.id.suggesion)
        suggestionList.adapter = suggestionAdapter
        suggestionList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fetchSuggestions()
        refreshRecyclerView()
        binding.textView6.text = decodedToken.username
        Glide.with(this).load(decodedToken.image)
            .circleCrop()
            .override(170, 170)
            .into(binding.profileim)

        val usernameTextView: TextView = navigationView.getHeaderView(0).findViewById(R.id.username) as TextView

        // Set the retrieved username to the TextView in the navigation drawer
        usernameTextView.text = decodedToken.email

        Glide.with(this).load(decodedToken.image)
            .circleCrop()
            .override(170, 170)
            .into(navigationView.getHeaderView(0).findViewById(R.id.profileimage))

        val educationAdapter = EducationAdapter(emptyList())
        educationList = findViewById(R.id.educationList)
        educationList.adapter = educationAdapter
        educationList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fab2: FloatingActionButton = findViewById(R.id.fab2)
        fab2.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.recherche.setOnClickListener() {
            startActivity(Intent(this, SearchUsersActivity::class.java))
        }
        apiFormation = ApiFormationEducation.create()
        fetchEducation()
        navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->

            when (menuItem.itemId) {
                R.id.formation -> {
                    startActivity(Intent(this, FormationMain::class.java))
                }
                R.id.education -> {
                    startActivity(Intent(this, OppoptunityActivity::class.java))
                }
                R.id.nav_item3 -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.nav_item1 -> {
                    startActivity(Intent(this, CommunityActivity::class.java))
                }
                R.id.nav_hospital -> {
                    startActivity(Intent(this, Addhopital::class.java))
                }
                R.id.nav_item2 -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Logout")
                    builder.setMessage("Are you sure you want to logout ?")
                    builder.setPositiveButton("Yes") { dialogInterface, which ->
                        logout()
                    }
                    builder.setNegativeButton("No") { dialogInterface, which ->
                        dialogInterface.dismiss()
                    }
                    builder.create().show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun refreshRecyclerView() {
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/experience/getExperiencesSortedByDate/")
            val experiences = parseApiResponse(apiResponse)

            Handler(Looper.getMainLooper()).post {
                val adapter = ExperienceAdapter(this, experiences) { selectedExperience ->
                    openExperienceActivity(selectedExperience.experienceId)
                }
                communityRecyclerView.adapter = adapter
            }
        }
    }

    private fun openExperienceActivity(experienceId: String) {
        val intent = Intent(this, ExperienceActivity::class.java)
        intent.putExtra("experienceId", experienceId)
        startActivity(intent)
    }

    private fun fetchDataFromApi(apiUrl: String): String {
        return try {
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun parseApiResponse(apiResponse: String): List<Experience> {
        val experiences = mutableListOf<Experience>()

        try {
            val json = JSONObject(apiResponse)
            val experiencesArray = json.getJSONArray("experiences")

            for (i in 0 until experiencesArray.length()) {
                val experienceJson = experiencesArray.getJSONObject(i)
                val id = experienceJson.getString("experienceId")
                val communityId = experienceJson.getString("communityId")
                val image = R.drawable.profile // Replace with actual image data
                val title = experienceJson.getString("title")
                val username = "username_placeholder"
                val experienceDate = experienceJson.optString("createdAt", null)
                val experienceText = experienceJson.optString("text", null)

                experiences.add(Experience(username, title, id, communityId, image, experienceDate, experienceText))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return experiences
    }

    private fun logout() {
        sessionManager = SessionManager(this)
        sessionManager.logout()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
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
    private fun fetchSuggestions() {
        // Make a Retrofit API call to get suggestions and update the adapter
        // Use the new method from Apiuser interface to get suggestions
        val apiUser = Apiuser.create()
        apiUser.getAllUsers().enqueue(object : Callback<List<JsonObject>?> {
            override fun onResponse(call: Call<List<JsonObject>?>, response: Response<List<JsonObject>?>) {
                if (response.isSuccessful) {
                    val userList = parseSuggestions(response.body())
                    if (userList != null) {
                        updateSuggestionList(userList)
                        Log.d("SuggestionAdapter", "Updating suggestion list with ${username} items")
                    }
                } else {
                    // Handle the unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<JsonObject>?>, t: Throwable) {

            }


        })
    }

    private fun parseSuggestions(jsonArray: List<JsonObject>?): List<LoginRequest> {
        val suggestionList = mutableListOf<LoginRequest>()

        // Check if the jsonArray is not null
        if (jsonArray != null) {
            // Parse the JSON array and create Suggestion objects
            for (jsonObject in jsonArray) {
                // Check if 'username' and 'profileImage' are not null
                val username = jsonObject?.get("username")?.asString
                val profileImage = jsonObject?.get("profileImage")?.asString

                // Check if both 'username' and 'profileImage' are not null before creating the LoginRequest
                if (username != null && profileImage != null) {
                    val suggestion = LoginRequest(username, profileImage)
                    suggestionList.add(suggestion)
                }
            }
        }

        return suggestionList
    }




    private fun updateSuggestionList(suggestions: List<LoginRequest>) {
        suggestionAdapter.setData(suggestions)
    }

}
