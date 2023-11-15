package tn.esprit.pdm.uikotlin.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.pdm.HomeActivity
import tn.esprit.pdm.ProfileActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.LoginBinding
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.uikotlin.home.HomePageActivity
import tn.esprit.pdm.utils.Apiuser
import java.util.Date

class LoginActivity : AppCompatActivity() {

    private val apiUser = Apiuser.create()
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyToken()

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatchers()

        binding.btnLogin.setOnClickListener {
            loginFunction()
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    private fun setupTextWatchers() {
        binding.tiEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.tiPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loginFunction() {
        if (validateEmail() && validatePassword()) {
            val loginRequest = LoginRequest(
                binding.tiEmail.text.toString(),
                binding.tiPassword.text.toString()
            )

            apiUser.seConnecter(loginRequest).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        sessionManager.setLogin(true)
                        sessionManager.setUserId(response.body()?.get("token").toString())
                        sessionManager.setUserEmail(response.body()?.get("token").toString())

                        val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    } else {
                        handleLoginError(response.errorBody()?.string())
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Snackbar.make(
                        binding.root,
                        "Failed to perform login. Please try again.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun verifyToken() {
        sessionManager = SessionManager(this)
        val token = sessionManager.getUserId().toString()

        if (!token.isNullOrBlank()) {
            try {
                val jwt = JWT(token)
                val expireDate: Date? = jwt.expiresAt

                if (expireDate != null) {
                    if (Date().after(expireDate)) {
                        sessionManager.logout()
                    } else {
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (exception: DecodeException) {
                sessionManager.logout()
                Log.e("Error", exception.toString())
            }
        }
    }

    private fun validateEmail(): Boolean {
        binding.tiEmailLayout.isErrorEnabled = false

        if (binding.tiEmail.text.toString().isEmpty()) {
            binding.tiEmailLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiEmail.requestFocus()
            return false
        } else {
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
        } else {
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        if (binding.tiPassword.text.toString().length < 6) {
            binding.tiPasswordLayout.error = getString(R.string.msg_check_your_characters)
            binding.tiPassword.requestFocus()
            return false
        } else {
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        return true
    }

    private fun handleLoginError(errorBody: String?) {
        val errorJsonObject = JsonParser.parseString(errorBody).asJsonObject
        if (errorJsonObject.has("message")) {
            val errorMessage = errorJsonObject.get("message").asString
            Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
        }

    }}