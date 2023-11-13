package tn.esprit.pdm.uikotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import tn.esprit.gamer.utils.MyStatics
import tn.esprit.gamer.utils.OTP_EMAIL
import tn.esprit.gamer.utils.OTP_MOBILE
import tn.esprit.pdm.HomeActivity
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.ActivityOtpValidationBinding
import tn.esprit.pdm.models.request.LoginRequest
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.utils.Apiuser
import java.util.Date


class OtpValidationActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityOtpValidationBinding
    val apiuser = Apiuser.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpValidationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = findViewById<View>(R.id.context_view)

        binding.tiCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.tiCode1.text.toString().length==1){
                    binding.tiCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.tiCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.tiCode2.text.toString().length==1){
                    binding.tiCode3.requestFocus()
                }else{
                    binding.tiCode1.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.tiCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.tiCode3.text.toString().length==1){
                    binding.tiCode4.requestFocus()
                }else{
                    binding.tiCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.tiCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.tiCode4.text.toString().isEmpty()){
                    binding.tiCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.btnReturn.setOnClickListener {
            finish()
        }

        binding.btnVerify.setOnClickListener {
            MyStatics.hideKeyboard(this, binding.btnVerify)
            if (checkOTP()){
                startActivity(Intent(this, ResetPasswordActivity::class.java))
            }else{
                Snackbar.make(contextView, getString(R.string.msg_error_wrong_code), Snackbar.LENGTH_SHORT).show()
            }

        }

        binding.btnResendCode.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun checkOTP(): Boolean {

        if (binding.tiCode1.text.toString().length == 1
            && binding.tiCode2.text.toString().length == 1
            && binding.tiCode3.text.toString().length == 1
            && binding.tiCode4.text.toString().length == 1){

            val newOTP = binding.tiCode1.text.toString().toInt() * 1000 + binding.tiCode2.text.toString().toInt() * 100 + binding.tiCode3.text.toString().toInt() * 10 + binding.tiCode4.text.toString().toInt()

            var otpValidation = 0

            if (intent.hasExtra(OTP_MOBILE))
                otpValidation = intent.getIntExtra(OTP_MOBILE, 0)
            else
                otpValidation = intent.getIntExtra(OTP_EMAIL, 0)
            val email = getEmailFromSharedPreferences()
            if (otpValidation == newOTP)
                resetcode()
                return true
        }

        return false
    }
    private fun resetcode() {
        val restCode = "${binding.tiCode1.text}${binding.tiCode2.text}${binding.tiCode3.text}${binding.tiCode4.text}".toInt()
        val signupRequest = LoginRequest(
            restCode  = restCode,
        email = getEmailFromSharedPreferences()

        )
        apiuser.resetCode(signupRequest)
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
            startActivity(Intent(this@OtpValidationActivity, ResetPasswordActivity::class.java))
            finish()
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
        Snackbar.make(binding.root, "Error: codeghalet", Snackbar.LENGTH_SHORT).show()
    }

    private fun getEmailFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)
        return sharedPreferences.getString(EMAIL, "") ?: ""
    }
}

