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
import androidx.core.content.ContextCompat
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

            // Set the custom InfoWindowAdapter
            googleMap?.setInfoWindowAdapter(CustomInfoWindowAdapter())

            googleMap?.setOnMapClickListener { latLng ->
                if (isWithinLuzon(latLng)) {
                    // Update the marker position
                    currentMarker?.position = latLng
                    currentMarker?.title = "Selected Location"
                    getAddressFromLatLng(latLng.latitude, latLng.longitude)
                    currentMarker?.showInfoWindow()

                    val Button: Button = findViewById(R.id.book_button)
                    Button.isEnabled = true;
                    Button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.main_blue)
                } else {
                    currentMarker?.position = latLng
                    currentMarker?.title = "Selected Location"

                    val Button: Button = findViewById(R.id.book_button)
                    Button.isEnabled = false;
                    Button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.grey_text)

                    val textView: TextView = findViewById(R.id.et_address)
                    textView.text = "Unreachable area! Please elect another..."
                    Toast.makeText(this, "This area is not reachable!", Toast.LENGTH_SHORT).show()
                }
            }

            currentMarker = googleMap?.addMarker(MarkerOptions().position(defaultLocation).title("Selected Location"))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

            // Enable zoom controls and settings
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isCompassEnabled = true

        } catch (e: Exception) {
            Log.e("MapsActivity", "Error in onMapReady", e)
            Toast.makeText(this, "Error configuring map: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /*private fun isWithinPhilippines(latLng: LatLng): Boolean {
        val minLatitude = 4.5
        val maxLatitude = 21.5
        val minLongitude = 116.0
        val maxLongitude = 126.5

        return latLng.latitude in minLatitude..maxLatitude && latLng.longitude in minLongitude..maxLongitude
    }*/

    private fun isWithinLuzon(latLng: LatLng): Boolean {
        // Define the polygon for Luzon Island using latitude and longitude
        val luzonPolygon = listOf(
            LatLng(18.78, 121.77), // Northernmost point (Babuyan Islands excluded)
            LatLng(16.25, 123.10), // Easternmost point
            LatLng(12.13, 121.94), // Southernmost point (Mindoro excluded)
            LatLng(13.15, 120.51), // Southwestern point
            LatLng(17.54, 119.97), // Northwestern point
            LatLng(18.78, 121.77)  // Closing the polygon
        )

        return isPointInPolygon(latLng, luzonPolygon)
    }

    private fun isPointInPolygon(point: LatLng, polygon: List<LatLng>): Boolean {
        var intersectCount = 0
        for (i in polygon.indices) {
            val vertex1 = polygon[i]
            val vertex2 = polygon[(i + 1) % polygon.size]

            // Check if the point is within a horizontal boundary of the polygon edge
            if ((point.latitude > vertex1.latitude) != (point.latitude > vertex2.latitude)) {
                val intersectionLongitude = vertex1.longitude +
                        (point.latitude - vertex1.latitude) * (vertex2.longitude - vertex1.longitude) /
                        (vertex2.latitude - vertex1.latitude)
                if (point.longitude < intersectionLongitude) {
                    intersectCount++
                }
            }
        }
        // If the number of intersections is odd, the point is inside the polygon
        return intersectCount % 2 == 1
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
