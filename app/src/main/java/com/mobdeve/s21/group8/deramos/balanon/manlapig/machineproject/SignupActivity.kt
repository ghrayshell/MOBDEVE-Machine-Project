package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etBirthday: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnCreateAccount: Button
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etFullName = findViewById(R.id.et_fullname)
        etNumber = findViewById(R.id.et_number)
        etBirthday = findViewById(R.id.et_birthday)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnCreateAccount = findViewById<AppCompatButton>(R.id.btn_create_account)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        fAuth = FirebaseAuth.getInstance()

        if(fAuth.currentUser != null){
            // Navigate to Main Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SignupActivity
        }

        // Account Creation
        btnCreateAccount.setOnClickListener {
            val username = etFullName.text.toString().trim()
            val number = etNumber.text.toString().trim()
            val birthday = etBirthday.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirm_password = etConfirmPassword.text.toString().trim()


            if (username.isEmpty() || number.isEmpty() || birthday.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
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

            if (password != confirm_password) {
                Toast.makeText(this, "Retyped password doesn't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val user = fAuth.currentUser
                        //saveUserInfoToFirestore(user?.uid, username.toString(), email.toString(), profileImg)
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                        // Navigate to Main Activity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close SignupActivity
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        // Back to LandingActivity
        btnBack.setOnClickListener {
            finish() // Close SignupActivity and return to LandingActivity
        }
    }
}
