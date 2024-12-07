package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityMainBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart.MapsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_appointments, R.id.navigation_catalogue, R.id.navigation_cart
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /*val testMyCart: Button = binding.testMyCart
        testMyCart.setOnClickListener {
            val i = Intent(this, MapsActivity::class.java)
            startActivity(i)
            Toast.makeText(this, "Launching MapsActivity", Toast.LENGTH_SHORT).show()
        }*/
    }
}