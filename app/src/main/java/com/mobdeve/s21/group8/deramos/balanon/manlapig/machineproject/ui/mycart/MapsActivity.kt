package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityMapsBinding
import java.util.Locale
import java.io.IOException
import com.google.android.gms.maps.model.Marker
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.LandingActivity


private var currentMarker: Marker? = null

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding // Declare binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize MapView
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Back Button
        binding.ivBackMap.setOnClickListener {
            onBackPressed() // Imitates back button press
        }

        // Logout Button
        binding.ivLogoutMap.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Successfully logged out!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Configure the map (set style, markers, etc.)
        try {
            googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )

            val defaultLocation = LatLng(14.5995, 120.9842) // Example: Manila, Philippines
            googleMap?.addMarker(MarkerOptions().position(defaultLocation).title("Selected Location"))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isCompassEnabled = true

        } catch (e: Exception) {
            Log.e("MapsActivity", "Error in onMapReady", e)
            Toast.makeText(this, "Error configuring map: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
