package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton // Use ImageButton here
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<AppCompatButton>(R.id.btn_login)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        // Log In
        btnLogin.setOnClickListener {
            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the LoginActivity
        }
        btnBack.setOnClickListener {
            finish() // Close LoginActivity and return to LandingActivity
        }
    }
}
