package br.com.mfet.emovie.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.mfet.emovie.utils.extensions.Extensions.toast
import br.com.mfet.emovie.utils.FirebaseUtils.firebaseAuth
import br.com.mfet.emovie.R
import br.com.mfet.emovie.databinding.ActivitySignInBinding
import br.com.mfet.emovie.presentation.MainActivity

class SignInActivity : AppCompatActivity() {
    private val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(layoutInflater)
    }
    private var signInInputsArray: Array<EditText>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(TIMEOUT)
        setTheme(R.style.Theme_Emovie)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViews()
        setupListeners()
    }

    private fun setupViews() = with(binding) {
        signInInputsArray = arrayOf(etSignInEmail, etSignInPassword)
    }

    private fun setupListeners() = with(binding) {
        btnCreateAccount2.setOnClickListener {
            startActivity(CreateAccountActivity.getLaunchIntent(this@SignInActivity))
            finish()
        }

        btnSignIn.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser() = with(binding) {
        val signInEmail = etSignInEmail.text.toString().trim()
        val signInPassword = etSignInPassword.text.toString().trim()

        if (signInEmail.isNotEmpty() && signInPassword.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(MainActivity.getLaunchIntent(this@SignInActivity))
                        toast(getString(R.string.connected_successfully))
                        finish()
                    } else {
                        toast(getString(R.string.connection_failed))
                    }
                }
        } else {
            signInInputsArray?.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = getString(R.string.required_field, input.hint)
                }
            }
        }
    }

    companion object {
        private const val TIMEOUT = 1000L
        fun getLaunchIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }
}