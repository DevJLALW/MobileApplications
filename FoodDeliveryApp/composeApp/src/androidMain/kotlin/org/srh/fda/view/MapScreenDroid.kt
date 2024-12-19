package org.srh.fda.view


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Locale

@Composable
fun MapScreenDroid(navController: NavController) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val mapView = remember { MapView(context) }
    val permissionGranted = remember { mutableStateOf(false) }
    val currentLocation = remember { mutableStateOf<GeoPoint?>(null) }
    val locationName = remember { mutableStateOf<String?>(null) }


    // Set a custom user agent for OsmDroid
    LaunchedEffect(context) {
       // Configuration.getInstance().userAgentValue = "FoodDeliveryApp/1.0" // Customize the user agent
        Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))

    }
    // Using ActivityResultContracts to request permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted.value = isGranted
            Log.d("Permission", "Location permission granted")
            if (!isGranted) {
                Log.d("Permission", "Location permission denied")
            }
        }
    )

    // Permission Check
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission", "Requesting location permission")
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            permissionGranted.value = true
            Log.d("Permission", "Location permission already granted")
        }
    }


    // Show map only if permission is granted
    if (permissionGranted.value) {
//        LaunchedEffect(Unit) {
//            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                location?.let {
//                    currentLocation.value = GeoPoint(it.latitude, it.longitude)
//                    mapView.controller.setZoom(15.0)
//                    mapView.controller.setCenter(GeoPoint(it.latitude, it.longitude))
//                }
//            }
//        }
        LaunchedEffect(Unit) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("Location", "Current Location: Latitude=$latitude, Longitude=$longitude")

                    currentLocation.value = GeoPoint(latitude, longitude)

                    // Perform reverse geocoding
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            locationName.value = addresses[0].getAddressLine(0) // Full address
                            // locationName.value = addresses[0].locality // City
                            // locationName.value = addresses[0].countryName // Country
                        }
                    } catch (e: Exception) {
                        Log.e("Geocoder", "Failed to get location name: ${e.message}")
                    }
                    mapView.controller.setZoom(15.0)
                    mapView.controller.setCenter(currentLocation.value)
                } else {
                    Log.d("Location", "Location is null")
                }
            }.addOnFailureListener { exception ->
                Log.e("Location", "Failed to fetch location: ${exception.message}")
            }
        }

        LaunchedEffect(currentLocation.value) {
            currentLocation.value?.let { location ->
                val marker = Marker(mapView).apply {
                    position = location
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "You are here"
                }

                mapView.overlays.clear()
                mapView.overlays.add(marker)
                mapView.invalidate()
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        ) {
            Column {
                AndroidView(
                    factory = {
                        mapView.apply {
                            setTileSource(TileSourceFactory.MAPNIK)

                            setMultiTouchControls(true)

//                        currentLocation.value?.let { location ->
//                            val marker = Marker(mapView)
//                            marker.position = location
//
//                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM) // Anchor the marker at the bottom
//                            mapView.overlays.add(marker)
//                            marker.title = "You are here"
//                            mapView.invalidate() // Refresh the map to reflect the marker addition
//                            mapView
//                            Log.d("Map", "Marker added at Latitude=${location.latitude}, Longitude=${location.longitude}")
//                        }

                        }

                    },
                    modifier = Modifier.weight(1f)
                )


                Button(
                    onClick = {
                        currentLocation.value?.let { location ->
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("selectedLocation", locationName.value ?: "${location.latitude},${location.longitude}")
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Select This Location")
                }
            }
        }
    } else {
        // Show a message when permission is denied
        Text("Location permission is required to display the map.")
    }
}


