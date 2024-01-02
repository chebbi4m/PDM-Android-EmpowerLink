package tn.esprit.pdm.uikotlin.hospital
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.R
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView

class ReservationHospital : AppCompatActivity() {
    private var availablePlaces: Int = 10 // Example: Set the initial number of available places

    //@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_hospital)

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener { finish() }

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            Snackbar.make(it, "Réservation effectuée avec succès!", Snackbar.LENGTH_LONG).show()
        }

        val textViewTime = findViewById<TextView>(R.id.textViewTime)
        textViewTime.text = "10:00 AM" // Example: Set the desired time

        val textViewAvailablePlaces = findViewById<TextView>(R.id.textViewAvailablePlaces)
        textViewAvailablePlaces.text = availablePlaces.toString() // Display the initial number of available places
    }
}

