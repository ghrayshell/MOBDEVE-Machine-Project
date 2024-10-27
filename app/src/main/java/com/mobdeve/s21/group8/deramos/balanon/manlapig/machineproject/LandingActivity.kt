package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnSignup = findViewById<Button>(R.id.btn_signup)

        // Navigate to LoginActivity
        btnLogin.setOnClickListener { v: View? ->
            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            startActivity(intent)
        }

        // Navigate to SignupActivity
        btnSignup.setOnClickListener { v: View? ->
            val intent = Intent(
                this,
                SignupActivity::class.java
            )
            startActivity(intent)
        }
    }
}
