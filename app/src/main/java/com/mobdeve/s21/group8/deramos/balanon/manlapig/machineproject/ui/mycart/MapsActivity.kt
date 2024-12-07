package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart

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
import android.widget.TextView


private var currentMarker: Marker? = null

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_maps)

            mapView = findViewById(R.id.mapView)
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
        } catch (e: Exception) {
            Log.e("MapsActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error initializing map: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        try {
            // Custom map style JSON
            googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )

            // Add a marker in a default location (e.g., a city center) and move the camera
            val defaultLocation = LatLng(14.5995, 120.9842) // Manila, Philippines (example)
            currentMarker = googleMap?.addMarker(MarkerOptions().position(defaultLocation).title("Selected Location"))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

            // Set the custom InfoWindowAdapter
            googleMap?.setInfoWindowAdapter(CustomInfoWindowAdapter())

            googleMap?.setOnMapClickListener { latLng ->
                // Update the marker position
                currentMarker?.position = latLng

                // Optionally set a new title or snippet
                currentMarker?.title = "Selected Location"
                // Optionally retrieve the address
                getAddressFromLatLng(latLng.latitude, latLng.longitude)
                currentMarker?.showInfoWindow()
            }

            // Enable zoom controls and settings
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isCompassEnabled = true

            Log.d("MapsActivity", "Map is ready and configured")
        } catch (e: Exception) {
            Log.e("MapsActivity", "Error in onMapReady", e)
            Toast.makeText(this, "Error configuring map: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun getAddressFromLatLng(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0].getAddressLine(0) // Full address

                currentMarker?.snippet = "$address"

                val textView: TextView = findViewById(R.id.et_address)
                textView.text = "$address"

                //Toast.makeText(this, "Address: $address", Toast.LENGTH_SHORT).show()
            } else {
                val textView: TextView = findViewById(R.id.et_address)
                textView.text = "No address found!"
                //Toast.makeText(this, "No address found!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error fetching address: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {

        private val infoWindowView = layoutInflater.inflate(R.layout.custom_info_window, null)

        override fun getInfoWindow(marker: Marker): View? {
            // Default info window frame
            return null
        }

        override fun getInfoContents(marker: Marker): View {
            // Populate custom layout with marker data
            val snippetView = infoWindowView.findViewById<TextView>(R.id.snippet)
            snippetView.text = marker.snippet

            // Hide the title if not needed
            val titleView = infoWindowView.findViewById<TextView>(R.id.title)
            if (marker.title.isNullOrEmpty()) {
                titleView.visibility = View.GONE
            } else {
                titleView.visibility = View.VISIBLE
                titleView.text = marker.title
            }

            return infoWindowView
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