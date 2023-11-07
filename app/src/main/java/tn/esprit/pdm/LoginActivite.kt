package tn.esprit.pdm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.telecom.Call
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.ArrayMap
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import tn.esprit.gamer.utils.MyStatics
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding
import tn.esprit.pdm.databinding.LoginBinding
import tn.esprit.pdm.models.User
import tn.esprit.pdm.utils.Apiuser
import javax.security.auth.callback.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


const val PREF_NAME = "DATA_CV_PREF"
const val emailfull = "email"
const val fullname = "fullname"
const val passwords = "passwords"
const val myuser = "myuser"
const val USER_ID = "USER_ID"
const val Facebookk = "FALSE"
const val IS_REMEMBRED = "remembred"

class LoginActivite : AppCompatActivity() {
    lateinit var mainIntent : Intent
    private lateinit var binding: LoginBinding
    lateinit var mSharedPref: SharedPreferences
    lateinit var fullname: TextInputEditText
    lateinit var email: TextInputEditText
    var gson = Gson()
    lateinit var password: TextInputEditText

    lateinit var txtLayoutLogin: TextInputLayout
    lateinit var cbRememberMe: CheckBox

    lateinit var txtLayoutPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        val apiuser= Apiuser.create()
        // apiuser.login().enqueue(object :Callback<User>)
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        password = findViewById(R.id.tiPassword)
        email = findViewById(R.id.tiEmail)
        txtLayoutLogin = findViewById(R.id.tiEmailLayout)
        txtLayoutPassword = findViewById(R.id.tiPasswordLayout)
        cbRememberMe = findViewById(R.id.btnRememberMe)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        if (mSharedPref.getBoolean(IS_REMEMBRED, false)) {
            val mainIntent = Intent(this, HomeActivity::class.java)

            startActivity(mainIntent)
            finish()
        }


        val contextView = findViewById<View>(R.id.context_view)


        binding.btnLogin.setOnClickListener {
            txtLayoutLogin!!.error = null
            txtLayoutPassword!!.error = null




            if (email?.text!!.isEmpty()) {
                txtLayoutLogin!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (!isEmailValid(email?.text.toString())){
                txtLayoutLogin!!.error = "Check your email !"

                return@setOnClickListener
            }
            if (password?.text!!.isEmpty()) {
                txtLayoutPassword!!.error = "must not be empty"
                return@setOnClickListener
            }

            mSharedPref.edit().apply {
                putString(emailfull, email!!.text.toString())

                putString(passwords, password!!.text.toString())
                putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)

            }.apply()
            if (cbRememberMe.isChecked){
                mSharedPref.edit().apply{
                    putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)
                }.apply()
            }
            mainIntent = Intent(this, HomeActivity::class.java)
            doLogin()


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
    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
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
    private fun doLogin(){

        val apiInterface = Apiuser.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = email.text.toString()
        jsonParams["password"] = password.text.toString()

        val jsonContent = JSONObject(jsonParams).toString()
        val body = jsonContent.toRequestBody("application/json; charset=utf-8".toMediaType())


        apiInterface.seConnecter(body).enqueue(object : retrofit2.Callback<User> {

            override fun onResponse(call: retrofit2.Call<User>, response: Response<User>) {

                val user = response.body()



                if (user != null){
                    Toast.makeText(this@LoginActivite, "Login Success", Toast.LENGTH_SHORT).show()


                    Log.d("user",user.toString())

                    val json = gson.toJson(user)
                    print("////////////////////////////////////////////////")
                    Log.d("json",json.toString())
                    mSharedPref.edit().apply {
                        putString(myuser, json)
                        putString(USER_ID,user.id)

                    }.apply()
                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivite, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
                Toast.makeText(this@LoginActivite, t.message, Toast.LENGTH_SHORT).show()

            }

        })


    }


}