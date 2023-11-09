package com.example.opportunity_pdm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.opportunity_pdm.databinding.ActivityMainBinding
import com.example.opportunity_pdm.R;

class MainActivity : AppCompatActivity() {


private lateinit var binding: ActivityMainBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("1","2","3","4")
        val adapter = ArrayAdapter(this,R.layout.lieu, items)
        val items1 = listOf("1","2","3","4")
        val adapter1 = ArrayAdapter(this,R.layout.type_de_contrat, items1)

        binding.autoCompleteTextView.setAdapter(adapter)
        binding.autoCompleteTextView2.setAdapter(adapter1)

    }



}

