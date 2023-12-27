package com.example.skyeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
                    MyScaffold()
                }
            }
        }
    }
}

@Composable
fun MyScaffold() {
    Scaffold(
        bottomBar = {
            BottomAppBarExample()
        }
    ) { paddingValues ->
        // Use the contentPadding parameter to apply padding to the content
        MapView(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun BottomAppBarExample() {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithLabel(
                onClick = { /* show settings page */ },
                iconRes = R.drawable.settings,
                label = "Settings"
            )
            IconWithLabel(
                onClick = { /* show weather modal */ },
                iconRes = R.drawable.weather,
                label = "Weather"
            )
            IconWithLabel(
                onClick = { /* show ar camera page */ },
                iconRes = R.drawable.camera,
                label = "AR Camera"
            )
            IconWithLabel(
                onClick = { /* show map type modal */ },
                iconRes = R.drawable.map,
                label = "Map Type"
            )
            IconWithLabel(
                onClick = { /* show filters modal */ },
                iconRes = R.drawable.filter,
                label = "Filters"
            )
        }
    }
}

@Composable
fun IconWithLabel(onClick: () -> Unit, @DrawableRes iconRes: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null, // Set to null as the label serves as content description
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
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