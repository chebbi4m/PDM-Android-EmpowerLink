package tn.esprit.pdm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_communities) // Set the content view

        // Create dummy data
        val dummyData = ArrayList<Community>()
        dummyData.add(Community(R.drawable.profile, "Community 1", "Category 1", "Objective 1"))
        dummyData.add(Community(R.drawable.profile, "Community 2", "Category 2", "Objective 2"))
        // Add more dummy data as needed

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CommunityAdapter(dummyData)
    }
}
