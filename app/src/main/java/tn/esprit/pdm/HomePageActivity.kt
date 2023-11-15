package tn.esprit.pdm.uikotlin.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.community.CommunityActivity
import tn.esprit.pdm.uikotlin.experience.ExperienceActivity
import tn.esprit.pdm.uikotlin.experience.ExperienceAdapter
import tn.esprit.pdm.uikotlin.login.ForgetPasswordActivity
import tn.esprit.pdm.uikotlin.login.LoginActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class HomePageActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var communityListView: ListView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    // Inside your HomePageActivity class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Set up the drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Set up ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up click listener for FAB2 to open the drawer
        val fab2: FloatingActionButton = findViewById(R.id.fab2)
        fab2.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Set up ListView
        communityListView = findViewById(R.id.homePageListView)
        refreshListView()

        // Set up item click listener for the navigation view
        navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.nav_item3 ->{
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.nav_item1 ->{
                    startActivity(Intent(this, CommunityActivity::class.java))
                }
                R.id.nav_item2 ->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Logout")
                    builder.setMessage("Are you sure you want to logout ?")
                    builder.setPositiveButton("Yes"){ dialogInterface, which ->
                        logout()
                    }
                    builder.setNegativeButton("No"){dialogInterface, which ->
                        dialogInterface.dismiss()
                    }
                    builder.create().show()
                }
            }
            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Open or close the drawer when the home/up button is pressed
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    // Inside your refreshListView function in HomePageActivity
    private fun refreshListView() {
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/experience/getExperiencesSortedByDate/")
            val experiences = parseApiResponse(apiResponse)

            // Update UI on the main thread
            Handler(Looper.getMainLooper()).post {
                val adapter = ExperienceAdapter(this, experiences) { selectedExperience ->
                    openExperienceActivity(selectedExperience.experienceId)
                }
                communityListView.adapter = adapter
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
                val category = experienceJson.getString("category")
                val username = "username_placeholder"
                val experienceDate = experienceJson.optString("createdAt", null)
                val experienceText = experienceJson.optString("text", null)

                experiences.add(Experience(username, title, id, communityId, image, experienceDate, experienceText ))
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

}
