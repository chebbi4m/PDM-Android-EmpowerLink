package tn.esprit.pdm.uikotlin.hospital

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityDetailsHospitalBinding
import tn.esprit.pdm.utils.ReservationHospital

class DetailsHospital : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsHospitalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setContentView(R.layout.activity_details_hospital)
        val button = findViewById<Button>(R.id.button)
        val textView11 = findViewById<TextView>(R.id.textView11)
        button.setOnClickListener {
            val startReservationHospital =
                Intent(this@DetailsHospital, ReservationHospital::class.java)
            startActivity(startReservationHospital)
        }
    }
}

