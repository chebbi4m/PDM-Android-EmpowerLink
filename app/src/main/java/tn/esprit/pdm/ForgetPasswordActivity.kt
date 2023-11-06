package tn.esprit.pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import tn.esprit.pdm.databinding.ActivityForgetPasswordBinding


class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding

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
                //validateEmail()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.btnReturn.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {

        }

        binding.btnSendSMS.setOnClickListener {


        }

       /* private fun validateEmail(): Boolean {
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
        }*/
    }
}