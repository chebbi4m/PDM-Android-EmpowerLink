package tn.esprit.pdm.uikotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View

import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject

import tn.esprit.pdm.databinding.ActivitySignUpBinding
import tn.esprit.pdm.utils.Apiuser
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import tn.esprit.gamer.utils.MyStatics
import tn.esprit.pdm.R
import tn.esprit.pdm.models.request.LoginRequest

class SignUpActivity : AppCompatActivity() {


   private  val apiuser = Apiuser.create()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)

        binding.tiFullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFullName()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

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

        binding.tiConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateConfirmPassword()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.signup.setOnClickListener {
            MyStatics.hideKeyboard(this, binding.signup)
            registerFunction()
            /* if (validateFullName() && validateEmail() && validatePassword() && validateConfirmPassword()){
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                Snackbar.make(contextView, getString(R.string.msg_error_inputs), Snackbar.LENGTH_SHORT).show()
            }*/
        }

        binding.btnTermsAndPolicy.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT)
                .show()
        }

        binding.btnReturn.setOnClickListener {
            finish()
        }
    }

    private fun validateFullName(): Boolean {
        binding.tiFullNameLayout.isErrorEnabled = false

        if (binding.tiFullName.text.toString().isEmpty()) {
            binding.tiFullNameLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiFullName.requestFocus()
            return false
        } else {
            binding.tiFullNameLayout.isErrorEnabled = false
        }

        if (binding.tiFullName.text.toString().length < 6) {
            binding.tiFullNameLayout.error = getString(R.string.msg_check_your_characters)
            binding.tiFullName.requestFocus()
            return false
        } else {
            binding.tiFullNameLayout.isErrorEnabled = false
        }

        return true
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

       /* if (Patterns.EMAIL_ADDRESS.matcher(binding.tiEmail.text.toString()).matches()) {
            binding.tiEmailLayout.error = getString(R.string.msg_check_your_email)
            binding.tiEmail.requestFocus()
            return false
        }
        else{
            binding.tiEmailLayout.isErrorEnabled = false
        }*/


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

    private fun validateConfirmPassword(): Boolean {
        binding.tiConfirmPasswordLayout.isErrorEnabled = false

        if (binding.tiConfirmPassword.text.toString().isEmpty()) {
            binding.tiConfirmPasswordLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiConfirmPassword.requestFocus()
            return false
        }else{
            binding.tiConfirmPasswordLayout.isErrorEnabled = false
        }

        if (binding.tiConfirmPassword.text.toString().length < 6) {
            binding.tiConfirmPasswordLayout.error = getString(R.string.msg_check_your_characters)
            binding.tiConfirmPassword.requestFocus()
            return false
        }else{
            binding.tiConfirmPasswordLayout.isErrorEnabled = false
        }

        if (binding.tiConfirmPassword.text.toString() != binding.tiPassword.text.toString()) {
            binding.tiConfirmPasswordLayout.error = getString(R.string.msg_check_your_confirm_Password)
            binding.tiPasswordLayout.error = getString(R.string.msg_check_your_confirm_Password)
            binding.tiConfirmPassword.requestFocus()
            return false
        }else{
            binding.tiConfirmPasswordLayout.isErrorEnabled = false
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        return true
    }



    private fun registerFunction() {
        if (validateEmail() && validatePassword()) {
            // Créer une instance de la classe Signuprequest avec les données de l'utilisateur
            val signupRequest = LoginRequest(
                email = binding.tiEmail.text.toString(),
                password = binding.tiPassword.text.toString(),
                username = binding.tiFullName.text.toString()
            )

            // Appeler la méthode d'inscription de l'API
            apiuser.sInscrire(signupRequest).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        // Traitement réussi, redirigez l'utilisateur vers l'activité d'accueil par exemple
                        startActivity(Intent(this@SignUpActivity, LoginActivite::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
                    } else {
                        // Afficher une erreur en cas de réponse non réussie
                        startActivity(Intent(this@SignUpActivity, LoginActivite::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // Gérer les erreurs liées à la connexion, etc.
                    Snackbar.make(binding.root, "Error: ${t.message}", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

}



