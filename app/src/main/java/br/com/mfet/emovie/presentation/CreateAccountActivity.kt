package br.com.mfet.emovie.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.mfet.emovie.databinding.ActivityCreateAccountBinding
import br.com.mfet.emovie.utils.extensions.Extensions.toast
import br.com.mfet.emovie.utils.FirebaseUtils.firebaseAuth
import br.com.mfet.emovie.utils.FirebaseUtils.firebaseUser
import com.google.firebase.auth.FirebaseUser

class CreateAccountActivity : AppCompatActivity() {
    private val binding: ActivityCreateAccountBinding by lazy {
        ActivityCreateAccountBinding.inflate(layoutInflater)
    }
    private var createAccountInputsArray: Array<EditText>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()

        createAccountInputsArray =
            arrayOf(binding.etEmail, binding.etPassword, binding.etConfirmPassword)
    }

    private fun setupListeners() = with(binding) {
        btnSignIn2.setOnClickListener {
            startActivity(SignInActivity.getLaunchIntent(this@CreateAccountActivity))
            toast("Por favor entre em sua conta")
            finish()
        }

        btnCreateAccount.setOnClickListener {
            signIn()
        }
    }
    // verifique se há um usuário conectado

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("Bem vindo de volta")
        }
    }

    private fun notEmpty(): Boolean = with(binding) {
        etEmail.text.toString().trim().isNotEmpty() &&
                etPassword.text.toString().trim().isNotEmpty() &&
                etConfirmPassword.text.toString().trim().isNotEmpty()
    }

    private fun identicalPassword(): Boolean = with(binding) {
        var identical = false
        if (notEmpty() &&
            etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray?.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} é obrigatório"
                }
            }
        } else {
            toast("senha não confere!")
        }
        return identical
    }

    private fun signIn() = with(binding) {
        if (identicalPassword()) {
            /* identicalPassword() retorna verdadeiro apenas quando as entradas não estão vazias
             e as senhas são idênticas
             */
            val userEmail = etEmail.text.toString().trim()
            val userPassword = etPassword.text.toString().trim()

            /*criar um usuário*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Conta criada com sucesso!")
                        sendEmailVerification()
                        startActivity(MainActivity.getLaunchIntent(this@CreateAccountActivity))
                        finish()
                    } else {
                        toast("Falha ao autenticar!")
                    }
                }
        }
    }

    /* enviar e-mail de verificação para o novo usuário. Isso vai apenas
       funciona se o usuário firebase não for nulo. */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Email enviado para ${binding.etEmail}")
                }
            }
        }
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, CreateAccountActivity::class.java)
    }
}
