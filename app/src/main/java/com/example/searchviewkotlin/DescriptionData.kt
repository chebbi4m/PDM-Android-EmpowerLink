package com.example.searchviewkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DescriptionData : AppCompatActivity(){


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description)

        val titledesc : TextView = findViewById(R.id.titleTv)
        val logodesc : ImageView = findViewById(R.id.logoIv)

        val bundle : Bundle?= intent.extras

        val title = bundle!!.getString("titleTv")
        val desc = bundle!!.getInt("logoIv")


        titledesc.text = title
        logodesc.setImageResource(desc)
    }
}