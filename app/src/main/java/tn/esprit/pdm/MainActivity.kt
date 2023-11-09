package tn.esprit.pdm


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, CommunityActivity::class.java))
        finish()  // Optional: Finish the main activity to prevent going back to it
    }
}
