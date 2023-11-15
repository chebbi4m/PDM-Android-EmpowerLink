package tn.esprit.pdm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.databinding.ActivityProfilBinding
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.login.EditProfileActivity
import tn.esprit.pdm.uikotlin.login.ForgetPasswordActivity
import tn.esprit.pdm.uikotlin.login.LoginActivity

class ProfileActivity: AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)
        binding.tiedit.setOnClickListener(){
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        val toolbar: Toolbar = binding.toolbar.appBar
        setSupportActionBar(toolbar)



        sessionManager = SessionManager(this)
        val token = sessionManager.getUserName().toString()

        // Décodez le token
        val decodedToken = sessionManager.decodeToken(token)

        // Utilisez les informations du token
        binding.tiusername.text = decodedToken.username
       // binding
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.nav_item2 ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Logout")
                builder.setMessage("Are you sure you want to logout ?")
                builder.setPositiveButton("Yes"){ dialogInterface, which ->
                    logout()
                }
                builder.setNegativeButton("No"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.create().show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun logout() {
        sessionManager = SessionManager(this)
        sessionManager.logout()
            val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}