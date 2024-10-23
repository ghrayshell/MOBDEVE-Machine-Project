package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val btnCreateAccount = findViewById<AppCompatButton>(R.id.btn_create_account)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        // Account Creation
        btnCreateAccount.setOnClickListener {
            // Navigate to Main Activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SignupActivity
        }

        // Back to LandingActivity
        btnBack.setOnClickListener {
            finish() // Close SignupActivity and return to LandingActivity
        }
    }
}
