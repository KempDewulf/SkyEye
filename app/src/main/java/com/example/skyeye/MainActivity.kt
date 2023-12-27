package com.example.skyeye

import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.skyeye.ui.theme.SkyEyeTheme
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyEyeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Homescreen()
                }
            }
        }
    }
}

@Composable
fun Homescreen() {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomAppBar()
        }
    ) { paddingValues ->
        // Use the contentPadding parameter to apply padding to the content
        MapView(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Menu, contentDescription = "menu", modifier = Modifier.size(32.dp))
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.AccountCircle, contentDescription = "avatar", modifier = Modifier.size(36.dp))
        }
    }
}

@Composable
fun BottomAppBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        Pair(R.drawable.settings, "Settings"),
        Pair(R.drawable.weather, "Weather"),
        Pair(R.drawable.camera, "Camera"),
        Pair(R.drawable.map, "Map Type"),
        Pair(R.drawable.filter, "Filters")
    )
    NavigationBar {
        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = icon), contentDescription = label) },
                label = { Text(label) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Composable
fun MapView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            Mapbox.getInstance(context)
            val mapView = com.mapbox.mapboxsdk.maps.MapView(context)
            val styleUrl = "https://api.maptiler.com/maps/basic-v2/style.json?key=OZkqnFxcrUbHDpJQ5a3K";
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                // Set the style after mapView was loaded
                map.setStyle(styleUrl) {
                    map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                    map.uiSettings.compassGravity = Gravity.BOTTOM or Gravity.START
                    map.uiSettings.setCompassMargins(40, 0, 0, 40)
                    map.uiSettings.isAttributionEnabled = false
                    map.uiSettings.isLogoEnabled = false
                    map.uiSettings.setCompassFadeFacingNorth(false)
                    // Set the map view center
                    map.cameraPosition = CameraPosition.Builder()
                        .target(LatLng(50.5, 4.47))
                        .zoom(3.5)
                        .bearing(2.0)
                        .build()
                }
            }
            mapView
        }
    )
}

