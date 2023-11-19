package tn.esprit.pdm.uikotlin.serviceSocial

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R

class SocialServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_service)
        val listView: ListView = findViewById(R.id.HospitalListView)
        val listItems = arrayOf("Mongi slim", "Charlis Nicolle", "Hbib Thameur",
            "Fttouma Bourguiba")

        val listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = listAdapter

        listView.setOnItemClickListener { parent, view, position, id->
            val selectedItem = parent.getItemAtPosition(position)   as String
            Toast.makeText( this,"you have clicked on: $selectedItem",
                Toast.LENGTH_SHORT).show()
        }


    }


}