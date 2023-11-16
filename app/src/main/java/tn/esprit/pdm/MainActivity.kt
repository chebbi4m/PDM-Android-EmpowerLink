package tn.esprit.pdm


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import tn.esprit.pdm.databinding.ActivityMainBinding
import tn.esprit.pdm.uikotlin.formation.FormationMain
import tn.esprit.pdm.uikotlin.login.LoginActivity
import tn.esprit.pdm.uikotlin.login.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var keep: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { keep }
        Handler(Looper.getMainLooper()).postDelayed({
            keep = false
        }, 1000)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val contextView=binding.contextView

        binding.btnLoginwelcome.setOnClickListener{
            startActivity(Intent(this, FormationMain::class.java))
        }
        binding.btnCreateAccount.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }



}
