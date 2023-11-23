package tn.esprit.pdm.uikotlin.login


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

import tn.esprit.gamer.utils.MyStatics
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityResetPasswordBinding
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.utils.Apiuser

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    val apiuser = Apiuser.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)

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

        binding.btnReturn.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            MyStatics.hideKeyboard(this, binding.btnSubmit)
            if (validatePassword() && validateConfirmPassword()){
             changerPassword()

                finish()
            }else{
                Snackbar.make(contextView, getString(R.string.msg_error_inputs), Snackbar.LENGTH_SHORT).show()
            }
        }

    }
    private fun changerPassword(){

        val changerpassword = LoginRequest(
            password = binding.tiPassword.text.toString(),
            email = getEmailFromSharedPreferences()
        )
        apiuser.changerPassword(changerpassword)
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
            startActivity(Intent(this@ResetPasswordActivity, LoginActivite::class.java).apply {   addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK) })
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

        if (!binding.tiConfirmPassword.text.toString().equals(binding.tiPassword.text.toString())) {
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
    private fun getEmailFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)
        return sharedPreferences.getString(EMAIL, "") ?: ""
    }
}