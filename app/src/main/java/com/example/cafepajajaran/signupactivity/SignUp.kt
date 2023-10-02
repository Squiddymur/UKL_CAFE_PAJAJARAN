package com.example.cafepajajaran.signupactivity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cafepajajaran.R
import com.example.cafepajajaran.loginactivity.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    lateinit var tvRedirectLogin: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // Deklarasi dan inisialisasi userTypeSpinner
        val userTypeSpinner = findViewById<Spinner>(R.id.userTypeSpinner)

        val userTypes = arrayOf("admin", "kasir")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userTypes)
        userTypeSpinner.adapter = adapter


        // Initialize views
        emailEditText = findViewById(R.id.etSEmailAddress)
        passwordEditText = findViewById(R.id.etSPassword)
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
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val userType = userTypeSpinner.selectedItem.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseHelper.createAccount(email, password, userType) { isSuccess ->
                    if (isSuccess) {
                        val user = FirebaseHelper.getCurrentUser()
                        if (user != null) {
                            FirebaseHelper.saveUserTypeToDatabase(
                                user.uid,
                                userType
                            ) { saveSuccess ->
                                if (saveSuccess) {
                                    Toast.makeText(
                                        this,
                                        "Account created successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, Login::class.java))
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this,
                                        "Account creation failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Please enter email and password.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
