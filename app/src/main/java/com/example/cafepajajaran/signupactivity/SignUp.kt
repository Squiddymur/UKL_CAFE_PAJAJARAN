package com.example.cafepajajaran.signupactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cafepajajaran.R
import com.example.cafepajajaran.loginactivity.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    lateinit var tvRedirectLogin: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize views
        emailEditText = findViewById(R.id.etSEmailAddress)
        passwordEditText = findViewById(R.id.etSPassword)
        confirmPasswordEditText = findViewById(R.id.etSConfPassword)
        signUpButton = findViewById(R.id.btnSSigned)
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()



        // switching from signUp Activity to Login Activity
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }




        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User registration successful
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            // Save additional user information to the database
                            val userRef = database.reference.child("users").child(userId)
                            userRef.child("email").setValue(email)
                        }

                        // Redirect to login page
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    } else {
                        // User registration failed
                        Toast.makeText(
                            this,
                            "Sign up failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
