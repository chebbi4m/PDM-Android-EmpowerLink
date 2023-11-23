package tn.esprit.pdm


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import tn.esprit.pdm.databinding.ActivityMainBinding
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.HomePageActivity
import tn.esprit.pdm.uikotlin.login.LoginActivite
import tn.esprit.pdm.uikotlin.login.SignUpActivity
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityMainBinding
    private var keep: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        verifToken()
        splashScreen.setKeepOnScreenCondition { keep }
        Handler(Looper.getMainLooper()).postDelayed({
            keep = false
        }, 1000)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val contextView=binding.contextView

        binding.btnLoginwelcome.setOnClickListener{
            startActivity(Intent(this, LoginActivite::class.java))


        }
        binding.btnCreateAccount.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
    private fun verifToken() {
        sessionManager = SessionManager(this)
        val token = sessionManager.getToken().toString();
        Log.d("Token", token)
        if (token!=null){
            try{
                val jwt = JWT(token)
                val expireDate : Date? = jwt.expiresAt

                if (expireDate != null){
                    if (Date().after(expireDate)){
                        sessionManager.logout()
                        val intent = Intent(this, LoginActivite::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }else {
                        val intent = Intent(this, HomePageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }
            }catch(exception: DecodeException){
                sessionManager.logout()

                Log.d( "Error", exception.toString())
            }
        }else {
            val intent = Intent(this, LoginActivite::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }



}
