package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton // Use ImageButton here
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var fAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById<AppCompatButton>(R.id.btn_login)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        fAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        if(fAuth.currentUser != null){
            // Navigate to Main Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SignupActivity
        }

        // Log In
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password.length < 6){
                etPassword.error = "Password must be 6 characters or more!"
                return@setOnClickListener
            }

            fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = fAuth.currentUser
                        val userId = user?.uid
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        if (userId != null) {
                            // Fetch user data from Firestore
                            db.collection("users").document(userId).get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        val name = document.getString("fullName")

                                        // Use the data (e.g., display in UI or save locally)
                                        Toast.makeText(this, "Welcome, $name!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error loading data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "User ID is null.", Toast.LENGTH_SHORT).show()
                        }

                        // Navigate to MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close the LoginActivity
                    } else {
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
                            is FirebaseAuthInvalidUserException -> "No account found with this email"
                            else -> "Login failed: ${task.exception?.message}"
                        }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }
        btnBack.setOnClickListener {
            finish() // Close LoginActivity and return to LandingActivity
        }
    }
}
