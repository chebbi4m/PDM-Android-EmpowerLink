package tn.esprit.pdm.uikotlin.community

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Community
import tn.esprit.pdm.uikotlin.experience.ExperienceActivity
import tn.esprit.pdm.utils.RetrofitInstance
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CommunityActivity : AppCompatActivity(), CreateCommunityFragment.OnCommunityCreatedListener {


    private lateinit var communityListView: ListView
    private lateinit var searchView: SearchView
    private lateinit var adapter: CommunityAdapter
    private var originalCommunities: List<Community> = emptyList()
    private var allCommunities: List<Community> = emptyList()
    private var pinnedCommunities: List<Community> = emptyList()
    private lateinit var currentCommunities: List<Community>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_communities)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val createCommunityFragment = CreateCommunityFragment()
            createCommunityFragment.setOnCommunityCreatedListener(this, this)
            createCommunityFragment.show(supportFragmentManager, "CreateCommunityFragment")
        }

        // Set up ListView and SearchView
        communityListView = findViewById(R.id.communityListView)
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCommunities(newText)
                return true
            }
        })
        setupButtons()
        switchToAllCommunities()
    }

    private fun setupButtons() {
        val pinnedButton: Button = findViewById(R.id.pinnedButton)
        pinnedButton.setOnClickListener {
            switchToPinnedCommunities()
            println("Pinned button clicked")
        }

        val allButton: Button = findViewById(R.id.allButton)
        allButton.setOnClickListener {
            switchToAllCommunities()
            println("All button clicked")
        }
    }
    private fun switchToAllCommunities() {
        currentCommunities = allCommunities
        refreshListView()
        println("Switched to All Communities")
    }

    private fun switchToPinnedCommunities() {
        currentCommunities = originalCommunities.filter { it.title.contains(searchView.query, true) && it.pinned.contains("mohamed") }
        refreshListView()
        println("Switched to Pinned Communities")
    }

    private fun filterCommunities(query: String?) {
        val filteredCommunities = if (query.isNullOrBlank()) {
            originalCommunities
        } else {
            originalCommunities.filter { it.title.startsWith(query, ignoreCase = true) }
        }
        adapter.clear()
        adapter.addAll(filteredCommunities)
        adapter.notifyDataSetChanged()
    }


    private fun refreshListView() {
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/community/getAllCommunities/")
            val communities = parseApiResponse(apiResponse)

            // Update UI on the main thread
            Handler(Looper.getMainLooper()).post {
                originalCommunities = communities

                if (!::adapter.isInitialized) {
                    adapter = CommunityAdapter(
                        this,
                        communities,
                        { selectedCommunity ->
                            val communityId = selectedCommunity.communityId

                            // Open ExperienceActivity with the community ID
                            val intent = Intent(this, ExperienceActivity::class.java)
                            intent.putExtra("communityId", communityId.toInt())
                            startActivity(intent)
                        }
                    )

                    communityListView.adapter = adapter
                } else {
                    // If the adapter is already initialized, just update the data
                    adapter.clear()
                    adapter.addAll(communities)
                    adapter.notifyDataSetChanged()
                }
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
                val communityId = communityJson.getInt("communityId") // Assuming communityId is an integer in MongoDB
                val image = R.drawable.profile // Replace with actual image data
                val title = communityJson.getString("name")
                val objectif = communityJson.getString("objectif")
                val category = communityJson.getString("category")
                val username = communityJson.getString("username")

                val pendingList = parseStringList(communityJson.getJSONArray("pending"))
                val membersList = parseStringList(communityJson.getJSONArray("members"))
                val pinnedList = parseStringList(communityJson.getJSONArray("pinned"))

                communities.add(
                    Community(
                        communityId,
                        image,
                        title,
                        objectif,
                        category,
                        username,
                        pendingList,
                        membersList,
                        pinnedList
                    )
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return communities
    }

    private fun parseStringList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }


    override fun onCommunityCreated() {
        fetchAndRefreshList()
    }

    fun fetchAndRefreshList() {
        thread {
            val apiResponse = fetchDataFromApi("http://10.0.2.2:9090/community/getAllCommunities/")
            val communities = parseApiResponse(apiResponse)

            // Separate pinned communities from others
            val pinnedCommunities = communities.filter { it.pinned.contains("wassim") }

            // Sort pinned communities by the highest number of members in descending order
            val sortedPinnedCommunities = pinnedCommunities.sortedByDescending { it.members.size }

            // Separate non-pinned communities
            val otherCommunities = communities.filter { !it.pinned.contains("wassim") }

            // Sort non-pinned communities by the highest number of members in descending order
            val sortedOtherCommunities = otherCommunities.sortedByDescending { it.members.size }

            // Combine the lists with pinned communities at the top
            val finalSortedCommunities = sortedPinnedCommunities + sortedOtherCommunities

            // Update UI on the main thread
            Handler(Looper.getMainLooper()).post {
                allCommunities = finalSortedCommunities // Initialize allCommunities
                originalCommunities = finalSortedCommunities
                adapter = CommunityAdapter(
                    this,
                    finalSortedCommunities,
                    { selectedCommunity ->
                        // Handle item click
                        val communityId = selectedCommunity.communityId
                        // Open ExperienceActivity with the community ID
                        val intent = Intent(this, ExperienceActivity::class.java)
                        intent.putExtra("communityId", communityId.toInt())
                        startActivity(intent)
                    }
                )

                // Set the adapter to the ListView
                communityListView.adapter = adapter
            }
        }
    }


}