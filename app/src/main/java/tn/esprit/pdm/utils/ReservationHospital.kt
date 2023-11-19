package tn.esprit.pdm.utils

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R

class ReservationHospital : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_hospital)
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener { finish() }
    }
}