package tn.esprit.pdm.uikotlin.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement

import retrofit2.Call
import retrofit2.Response
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.models.request.LoginRequest

import tn.esprit.pdm.utils.Apiuser

const val PREF_FILE = "USER_PREF"
const val EMAIL = "EMAIL"

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding
    private val apiuser = Apiuser.create()
    private val PREF_FILE = "USER_PREF"
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)
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

    }

    private fun validateEmail(): Boolean {
        binding.tiEmailLayout.error = null

        val email = binding.tiEmail.text.toString().trim()

        if (email.isEmpty()) {
            binding.tiEmailLayout.error = "Email is required"
            binding.tiEmail.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tiEmailLayout.error = "Enter a valid email address"
            binding.tiEmail.requestFocus()
            return false
        }

        return true
    }

    private fun sendEmail() {
        // Validate the email before proceeding
        if (!validateEmail()) {
            return  // Exit the function if email validation fails
        }

        // Create a LoginRequest object with the email
        val signupRequest = LoginRequest(
            email = binding.tiEmail.text.toString()
        )

        // Save the email to SharedPreferences (if needed)
        val editor = mSharedPreferences.edit()
        editor.putString(EMAIL, binding.tiEmail.text.toString())
        editor.apply()

        // Call the API to send the password reset code
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
            startActivity(Intent(this@ForgetPasswordActivity, OtpValidationActivity::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
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
