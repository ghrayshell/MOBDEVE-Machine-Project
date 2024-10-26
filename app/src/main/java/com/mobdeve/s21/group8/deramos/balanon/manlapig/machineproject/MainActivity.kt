package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityMainBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.CatalogueActivity
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.MyCartActivity

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
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //test buttons
        val testCatalogue: Button = binding.testCatalogue
        testCatalogue.setOnClickListener{ view ->
            val i = Intent(this, CatalogueActivity::class.java)
            startActivity(i)
        }

        val testMyCart: Button = binding.testMyCart
        testMyCart.setOnClickListener{ view ->
            val i = Intent(this, MyCartActivity::class.java)
            startActivity(i)
        }

    }
}