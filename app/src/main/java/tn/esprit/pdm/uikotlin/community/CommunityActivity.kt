// CommunityActivity.kt
package tn.esprit.pdm.uikotlin.community

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Community
import tn.esprit.pdm.uikotlin.experience.ExperienceActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CommunityActivity : AppCompatActivity(), CreateCommunityFragment.OnCommunityCreatedListener{

    private lateinit var communityListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_communities) // Set the content view
        //sessionManager = SessionManager(this)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val createCommunityFragment = CreateCommunityFragment()
            createCommunityFragment.setOnCommunityCreatedListener(this, this)
            createCommunityFragment.show(supportFragmentManager, "CreateCommunityFragment")
        }

        // Set up ListView
        communityListView = findViewById(R.id.communityListView)
        refreshListView()
    }

    private fun refreshListView() {
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/community/getAllCommunities/")
            val communities = parseApiResponse(apiResponse)

            // Update UI on the main thread
            Handler(Looper.getMainLooper()).post {
                val adapter = CommunityAdapter(this, communities) { selectedCommunity ->
                    val communityId = selectedCommunity.id

                    // Open ExperienceActivity with the community ID
                    // In CommunityActivity.kt
                    val intent = Intent(this, ExperienceActivity::class.java)
                    intent.putExtra("communityId", communityId.toInt())
                    Log.d("IntentDebug", "Starting activity: ${ExperienceActivity::class.java.simpleName}")
                    startActivity(intent)

                }
                communityListView.adapter = adapter
            }
        }
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

    private fun parseApiResponse(apiResponse: String): List<Community> {
        val communities = mutableListOf<Community>()

        try {
            val json = JSONObject(apiResponse)
            val communitiesArray = json.getJSONArray("communities")

            for (i in 0 until communitiesArray.length()) {
                val communityJson = communitiesArray.getJSONObject(i)
                val id = communityJson.getLong("communityId")
                val image = R.drawable.profile // Replace with actual image data
                val title = communityJson.getString("name")
                val category = communityJson.getString("objectif")
                val username = "username_placeholder"

                // Provide the username parameter when creating the Community object
                communities.add(Community(id, image, title, category, username))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return communities
    }


    override fun onCommunityCreated() {
        // Handle the community creation event
        fetchAndRefreshList()
    }

    // Move fetchAndRefreshList outside of onCreate
    fun fetchAndRefreshList() {
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/community/getAllCommunities/")
            val communities = parseApiResponse(apiResponse)

            // Update UI on the main thread
            Handler(Looper.getMainLooper()).post {
                val adapter = CommunityAdapter(this, communities) { selectedCommunity ->
                    val communityId = selectedCommunity.id

                    // Open ExperienceActivity with the community ID
                    val intent = Intent(this, ExperienceActivity::class.java)
                    intent.putExtra("communityId", communityId.toInt())
                    startActivity(intent)
                }
                communityListView.adapter = adapter

            }
        }
    }
}