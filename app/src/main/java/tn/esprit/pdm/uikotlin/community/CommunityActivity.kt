package tn.esprit.pdm.uikotlin.community

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import tn.esprit.pdm.uikotlin.experience.ExperienceActivity
import tn.esprit.pdm.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import tn.esprit.pdm.models.Community

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_communities) // Set the content view

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val createCommunityFragment = CreateCommunityFragment()
            createCommunityFragment.show(supportFragmentManager, "CreateCommunityFragment")
        }

        // Fetch data from the API
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/community/getAllCommunities/")
            val communities = parseApiResponse(apiResponse)

            // Update UI on the main thread
            runOnUiThread {
                // Set up ListView
                val communityListView: ListView = findViewById(R.id.communityListView)
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

    private fun fetchDataFromApi(apiUrl: String): String {
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection

        try {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            return response.toString()
        } finally {
            connection.disconnect()
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
                val category = communityJson.getString("category")
                val objectif = communityJson.getString("objectif")

                communities.add(Community(id, image, title, category, objectif))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return communities
    }
}
