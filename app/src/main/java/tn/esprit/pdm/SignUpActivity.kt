package tn.esprit.pdm

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONObject

import tn.esprit.pdm.databinding.ActivitySignUpBinding
import tn.esprit.pdm.models.User
import tn.esprit.pdm.utils.Apiuser
import retrofit2.Call
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class SignUpActivity : AppCompatActivity() {

    lateinit var fullname: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var txtLayoutPassword: TextInputLayout
    lateinit var password: TextInputEditText
    lateinit var txtLayoutPasswordConfirmed: TextInputLayout
    lateinit var txtLayoutLogin: TextInputLayout
    lateinit var txtPasswordConfirmed: TextInputEditText
    lateinit var txtLayoutName: TextInputLayout

    lateinit var btnLogin: Button

    lateinit var mainIntent : Intent
    lateinit var buttonLogin: TextView
    var gson = Gson()
    lateinit var mSharedPref: SharedPreferences


    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        password = findViewById(R.id.tiPassword)
        email = findViewById(R.id.tiEmail)
        fullname = findViewById(R.id.tiFullName)
        btnLogin = findViewById(R.id.signup)
        txtLayoutPassword = findViewById(R.id.tiPasswordLayout)
        txtLayoutLogin = findViewById(R.id.tiEmailLayout)
        txtLayoutName = findViewById(R.id.tiFullNameLayout)
        txtPasswordConfirmed = findViewById(R.id.tiConfirmPassword)
        txtLayoutPasswordConfirmed = findViewById(R.id.tiConfirmPasswordLayout)


        val contextView = findViewById<View>(R.id.context_view)






        binding.signup.setOnClickListener {
            txtLayoutLogin.error = null
            txtLayoutPassword.error = null
            txtLayoutName.error = null
            txtLayoutPasswordConfirmed.error = null




            if (fullname.text!!.isEmpty()) {
                txtLayoutName.error = "must not be empty"
                return@setOnClickListener
            }
            if (email.text!!.isEmpty()) {
                txtLayoutLogin!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (!isEmailValid(email.text.toString())) {
                txtLayoutLogin!!.error = "Check your email !"

                return@setOnClickListener
            }
            if (password.text!!.isEmpty()) {
                txtLayoutPassword!!.error = "must not be empty"
                return@setOnClickListener
            }

            if (txtPasswordConfirmed.text!!.isEmpty()) {
                txtLayoutPasswordConfirmed!!.error = "must not be empty"
                return@setOnClickListener
            }

            if (password.text.toString() != txtPasswordConfirmed!!.text.toString()) {
                txtLayoutPassword.error = "Password don't match"
                txtLayoutPasswordConfirmed.error = "Password don't match"
                return@setOnClickListener
            }


            mainIntent = Intent(this, HomeActivity::class.java)

            doLogin()
        }



        binding.btnTermsAndPolicy.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT).show()
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
        }else{
            binding.tiFullNameLayout.isErrorEnabled = false
        }

        if (binding.tiFullName.text.toString().length < 6) {
            binding.tiFullNameLayout.error = getString(R.string.msg_check_your_characters)
            binding.tiFullName.requestFocus()
            return false
        }else{
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
    /*private fun register() {
        // Valider les champs du formulaire
        if (validateFullName() && validateEmail() && validatePassword()) {
            // Créer un objet User avec les données du formulaire
            val user = User(
                id = "", // L'ID sera généré côté serveur
                username = binding.tiFullName.text.toString(),
                email = binding.tiEmail.text.toString(),
                password = binding.tiPassword.text.toString(),
                address = null, // Ou une valeur par défaut
                birthday = null, // Ou une valeur par défaut
                firstname = null, // Ou une valeur par défaut
                lastname = null, // Ou une valeur par défaut
                image = null, // Ou une valeur par défaut
                number = null, // Ou une valeur par défaut
                reason = null, // Ou une valeur par défaut
                resetCode = null // Ou une valeur par défaut
            )

            // Créer une instance de l'interface Apiuser
            // val apiuser = Apiuser.cr

            // Appel à l'API pour l'enregistrement de l'utilisateur
            //val call = apiuser.registerUser(user)

            // Envoi de la requête de manière asynchrone


        }}*/
    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

    private fun doLogin(){

        val apiInterface=Apiuser.create()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
//put something inside the map, could be null
        jsonParams["email"] = email.text.toString()
        jsonParams["password"] = password.text.toString()
        jsonParams["username"] = fullname.text.toString()



        val jsonContent = JSONObject(jsonParams).toString()
        val body = jsonContent.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        apiInterface.SignIn(body).enqueue(object : retrofit2.Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                val user = response.body()

                if (user != null){
                    Toast.makeText(this@SignUpActivity, "Sign Up Success", Toast.LENGTH_SHORT).show()
                    Log.d("user",user.toString())

                    val json = gson.toJson(user)
                    print("////////////////////////////////////////////////")
                    Log.d("json",json.toString())
                    mSharedPref.edit().apply {
                        putString(myuser, json)

                    }.apply()
                    startActivity(mainIntent)
                    //startActivity(LoginBinding)
                    finish()
                }else{
                    Toast.makeText(this@SignUpActivity, "can not sign up", Toast.LENGTH_SHORT).show()
                }


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, t.message, Toast.LENGTH_SHORT).show()


                window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }

        })


    }
}