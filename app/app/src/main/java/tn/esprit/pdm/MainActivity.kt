package tn.esprit.pdm


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import tn.esprit.pdm.databinding.ActivityMainBinding
import tn.esprit.pdm.uikotlin.community.CommunityActivity
import tn.esprit.pdm.uikotlin.home.HomePageActivity
import tn.esprit.pdm.uikotlin.login.LoginActivite
import tn.esprit.pdm.uikotlin.login.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var keep: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomePageActivity::class.java))
        // finish()  // Comment out or remove this line
    }




}
