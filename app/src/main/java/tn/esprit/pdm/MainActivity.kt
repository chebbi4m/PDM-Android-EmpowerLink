package tn.esprit.pdm


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.pdm.uikotlin.community.CommunityActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, CommunityActivity::class.java))
        // finish()  // Comment out or remove this line
    }




}
