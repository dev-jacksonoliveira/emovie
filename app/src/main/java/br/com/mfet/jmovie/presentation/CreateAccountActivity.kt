package br.com.mfet.jmovie.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.mfet.jmovie.databinding.ActivityCreateAccountBinding
import br.com.mfet.jmovie.utils.extensions.Extensions.toast
import br.com.mfet.jmovie.utils.FirebaseUtils.firebaseAuth
import br.com.mfet.jmovie.utils.FirebaseUtils.firebaseUser
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.btnCreateAccount
import kotlinx.android.synthetic.main.activity_create_account.btnSignIn2
import kotlinx.android.synthetic.main.activity_create_account.etConfirmPassword
import kotlinx.android.synthetic.main.activity_create_account.etEmail
import kotlinx.android.synthetic.main.activity_create_account.etPassword

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)
        btnCreateAccount.setOnClickListener {
            signIn()
        }

        btnSignIn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            toast("Por favor entre em sua conta")
            finish()
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

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} é obrigatório"
                }
            }
        } else {
            toast("senha não confere!")
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {
            /* identicalPassword() retorna verdadeiro apenas quando as entradas não estão vazias
             e as senhas são idênticas
             */
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()

            /*criar um usuário*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Conta criada com sucesso!")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
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
                    toast("Email enviado para $userEmail")
                }
            }
        }
    }
}
