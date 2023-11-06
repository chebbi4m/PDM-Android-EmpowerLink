package tn.esprit.pdm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.telecom.Call
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import tn.esprit.gamer.utils.MyStatics
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.databinding.LoginBinding
import tn.esprit.pdm.models.User
import tn.esprit.pdm.utils.Apiuser
import javax.security.auth.callback.Callback

class LoginActivite : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var mSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        val apiuser= Apiuser.create()
       // apiuser.login().enqueue(object :Callback<User>)
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)
        binding.tiEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.tiPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })
        binding.btnLogin.setOnClickListener {


        }



        binding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

        binding.btnFacebookLogin.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT)
//                .setAction("ACTION") {
//                    // Responds to click on the action
//                }
                .show()
        }

        binding.btnGoogleLogin.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT)
//                .setAction("ACTION") {
//                    // Responds to click on the action
//                }
                .show()
        }






        //startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun validateEmail(): Boolean {
        binding.tiEmailLayout.isErrorEnabled = false

        if (binding.tiEmail.text.toString().isEmpty()) {
            binding.tiEmailLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiEmail.requestFocus()
            return false
        }else{
            binding.tiEmailLayout.isErrorEnabled = false
        }

        if (Patterns.EMAIL_ADDRESS.matcher(binding.tiEmail.text.toString()).matches()) {
            binding.tiEmailLayout.error = getString(R.string.msg_check_your_email)
            binding.tiEmail.requestFocus()
            return false
        }else{
            binding.tiEmailLayout.isErrorEnabled = false
        }

        return true
    }

    private fun validatePassword(): Boolean {
        binding.tiPasswordLayout.isErrorEnabled = false

        if (binding.tiPassword.text.toString().isEmpty()) {
            binding.tiPasswordLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiPassword.requestFocus()
            return false
        }else{
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        if (binding.tiPassword.text.toString().length < 6) {
            binding.tiPasswordLayout.error = getString(R.string.msg_check_your_characters)
            binding.tiPassword.requestFocus()
            return false
        }else{
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        return true
    }

}