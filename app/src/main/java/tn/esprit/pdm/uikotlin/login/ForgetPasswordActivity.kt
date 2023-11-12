package tn.esprit.pdm.uikotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement

import retrofit2.Call
import retrofit2.Response
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.models.request.LoginRequest

import tn.esprit.pdm.utils.Apiuser


class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding
    val apiuser = Apiuser.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
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

        binding.btnReturn.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            sendEmail()
        }

        binding.btnSendSMS.setOnClickListener {


        }


    }

    private fun validateEmail(): Boolean {
        binding.tiEmailLayout.isErrorEnabled = false

        if (binding.tiEmail.text.toString().isEmpty()) {

            binding.tiEmail.requestFocus()
            return false
        } else {
            binding.tiEmailLayout.isErrorEnabled = false
        }

        if (Patterns.EMAIL_ADDRESS.matcher(binding.tiEmail.text.toString()).matches()) {

            binding.tiEmail.requestFocus()
            return false
        } else {
            binding.tiEmailLayout.isErrorEnabled = false
        }

        return true
    }

    private fun sendEmail() {
        val signupRequest = LoginRequest(
            email = binding.tiEmail.text.toString()
        )

        apiuser.sendPasswordResetCode(signupRequest)
            .enqueue(object : retrofit2.Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {
                        handleSuccessfulResponse(response.body())
                    } else {
                        handleErrorResponse(response.code())
                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    handleFailure(t.message)
                }
            })
    }


    private fun handleSuccessfulResponse(responseBody: JsonElement?) {
        // Check if the response body is not null
        if (responseBody != null) {
            // Log the JSON response
            Log.d("API_RESPONSE", responseBody.toString())

            // Further processing if needed

            // Redirect the user to the OTP validation activity
            startActivity(Intent(this@ForgetPasswordActivity, OtpValidationActivity::class.java))
        } else {
            // Handle null response body
            handleFailure("Null response body")
        }
    }


    private fun handleErrorResponse(responseCode: Int) {
        // Display an error in case of an unsuccessful response
        Snackbar.make(binding.root, "Error: $responseCode", Snackbar.LENGTH_SHORT).show()
    }

    private fun handleFailure(errorMessage: String?) {
        // Handle errors related to connection, etc.
        Snackbar.make(binding.root, "Error: $errorMessage", Snackbar.LENGTH_SHORT).show()
    }
}