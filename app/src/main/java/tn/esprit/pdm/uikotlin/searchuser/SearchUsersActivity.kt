package tn.esprit.pdm.uikotlin.searchuser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.R
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.utils.Apiuser

class SearchUsersActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var userAdapter: UserAdapter // Assuming you have implemented UserAdapter
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_users)

        recyclerView = findViewById(R.id.recyclerViewUsers)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        searchEditText = findViewById(R.id.searchEditText)
        userAdapter = UserAdapter(this, emptyList()) // Initialize with an empty list

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        // Add a TextWatcher to detect changes in the searchEditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this example
            }

            override fun afterTextChanged(editable: Editable?) {
                val searchName = editable.toString()
                if (searchName.isNotBlank()) {
                    searchUsersByName(searchName)
                } else {
                    // Handle the case where the searchEditText is empty
                }
            }
        })
    }

    private fun searchUsersByName(name: String) {
        loadingProgressBar.visibility = View.VISIBLE

        val apiUser = Apiuser.create()
        val call = apiUser.searchUsersByName(name)

        call.enqueue(object : Callback<List<LoginRequest>> {
            override fun onResponse(call: Call<List<LoginRequest>>, response: Response<List<LoginRequest>>) {
                loadingProgressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        updateRecyclerView(users)
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<LoginRequest>>, t: Throwable) {
                loadingProgressBar.visibility = View.GONE
                // Handle failure
            }
        })
    }

    private fun updateRecyclerView(users: List<LoginRequest>) {
        userAdapter = UserAdapter(this, users)
        recyclerView.adapter = userAdapter
    }
}
