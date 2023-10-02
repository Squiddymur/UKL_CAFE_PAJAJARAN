package com.example.cafepajajaran.loginactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafepajajaran.MainActivity
import com.example.cafepajajaran.R
import com.example.cafepajajaran.kasiractivity.KasirActivity
import com.example.cafepajajaran.signupactivity.SignUp
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    private lateinit var tvRedirectSignUp: TextView
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // View Binding
        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmailAddress)
        etPass = findViewById(R.id.etPassword)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
        }
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPass.text.toString()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Panggil fungsi signIn dari FirebaseHelper
            FirebaseHelper.Login(email, password) { isSuccess ->
                if (isSuccess) {
                    val user = FirebaseHelper.getCurrentUser()
                    if (user != null) {
                        FirebaseHelper.getUserType(user.uid) { userType ->
                            if (userType == "admin") {
                                navigateToMainScreen()
                                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (userType == "kasir") {
                                navigateToKasirScreen()
                                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT)
                                    .show()
                            }else{
                                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }
        }
    }

    private fun navigateToKasirScreen() {
        val intent = Intent(this, KasirActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }
}
